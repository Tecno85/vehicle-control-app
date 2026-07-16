package com.ivanmadrid.vehiclecontrolapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDeletionRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun vehicleCrud_preservesDataAndValidatesPlateIgnoringCase() = runBlocking {
        val vehicleId = database.vehicleDao().insertVehicle(
            vehicleEntity(plate = "ABC123")
        ).toInt()

        assertTrue(database.vehicleDao().plateExists("abc123"))
        assertFalse(database.vehicleDao().plateExistsForOtherVehicle("ABC123", vehicleId))

        val storedVehicle = database.vehicleDao().getVehicleById(vehicleId).first()
        assertEquals("ABC123", storedVehicle?.plate)
        assertEquals(VehicleType.TAXI, storedVehicle?.type)
    }

    @Test
    fun deleteVehicleWithRelatedData_removesCompleteVehicleHistory() = runBlocking {
        val vehicleId = database.vehicleDao().insertVehicle(
            vehicleEntity(plate = "XYZ789")
        ).toInt()

        database.expenseDao().insertExpense(
            ExpenseEntity(
                vehicleId = vehicleId,
                date = "2026-07-16",
                category = ExpenseCategory.FUEL,
                amount = 70_000,
                description = "Combustible"
            )
        )
        database.noveltyDao().insertNovelty(
            NoveltyEntity(
                vehicleId = vehicleId,
                date = "2026-07-16",
                type = "Trabajo parcial",
                description = "Operación de medio día",
                priority = NoveltyPriority.MEDIUM,
                affectsIncome = true,
                incomeAdjustmentType = IncomeAdjustmentType.HALF_INCOME,
                adjustedIncomeAmount = null
            )
        )
        database.vehicleDocumentDao().insertDocument(
            VehicleDocumentEntity(
                vehicleId = vehicleId,
                type = VehicleDocumentType.SOAT,
                dueDate = "2027-07-16",
                notes = null
            )
        )

        VehicleDeletionRepository(database).deleteVehicleWithRelatedData(
            Vehicle(
                id = vehicleId,
                plate = "XYZ789",
                brand = "Kia",
                model = "Picanto",
                type = VehicleType.TAXI,
                status = "Activo",
                currentDriver = "Conductor de prueba",
                dailyFixedIncome = 180_000
            )
        )

        assertEquals(null, database.vehicleDao().getVehicleById(vehicleId).first())
        assertTrue(database.expenseDao().getExpensesByVehicle(vehicleId).first().isEmpty())
        assertTrue(database.noveltyDao().getNoveltiesByVehicle(vehicleId).first().isEmpty())
        assertTrue(database.vehicleDocumentDao().getDocumentsByVehicle(vehicleId).first().isEmpty())
    }

    @Test
    fun expenseCrud_ordersNewestFirstAndPersistsUpdates() = runBlocking {
        val vehicleId = database.vehicleDao().insertVehicle(
            vehicleEntity(plate = "EXP001")
        ).toInt()
        val olderExpenseId = database.expenseDao().insertExpense(
            expenseEntity(vehicleId = vehicleId, date = "2026-07-10", amount = 50_000)
        ).toInt()
        val newerExpenseId = database.expenseDao().insertExpense(
            expenseEntity(vehicleId = vehicleId, date = "2026-07-15", amount = 70_000)
        ).toInt()

        val orderedExpenses = database.expenseDao().getExpensesByVehicle(vehicleId).first()
        assertEquals(listOf(newerExpenseId, olderExpenseId), orderedExpenses.map { it.id })

        database.expenseDao().updateExpense(
            expenseEntity(
                id = newerExpenseId,
                vehicleId = vehicleId,
                date = "2026-07-15",
                amount = 75_000
            )
        )
        assertEquals(
            75_000L,
            database.expenseDao().getExpensesByVehicle(vehicleId).first().first().amount
        )

        database.expenseDao().deleteExpense(orderedExpenses.last())
        assertEquals(1, database.expenseDao().getExpensesByVehicle(vehicleId).first().size)
    }

    @Test
    fun noveltyCrud_preservesTaxiIncomeAdjustment() = runBlocking {
        val vehicleId = database.vehicleDao().insertVehicle(
            vehicleEntity(plate = "NOV001")
        ).toInt()
        val noveltyId = database.noveltyDao().insertNovelty(
            noveltyEntity(vehicleId = vehicleId)
        ).toInt()

        val storedNovelty = database.noveltyDao().getNoveltiesByVehicle(vehicleId).first().single()
        assertTrue(storedNovelty.affectsIncome)
        assertEquals(IncomeAdjustmentType.CUSTOM_AMOUNT, storedNovelty.incomeAdjustmentType)
        assertEquals(125_000L, storedNovelty.adjustedIncomeAmount)

        database.noveltyDao().updateNovelty(
            noveltyEntity(id = noveltyId, vehicleId = vehicleId).copy(
                priority = NoveltyPriority.HIGH
            )
        )
        assertEquals(
            NoveltyPriority.HIGH,
            database.noveltyDao().getNoveltiesByVehicle(vehicleId).first().single().priority
        )

        database.noveltyDao().deleteNovelty(
            database.noveltyDao().getNoveltiesByVehicle(vehicleId).first().single()
        )
        assertTrue(database.noveltyDao().getNoveltiesByVehicle(vehicleId).first().isEmpty())
    }

    @Test
    fun documentCrud_ordersByDueDateAndPersistsNotes() = runBlocking {
        val vehicleId = database.vehicleDao().insertVehicle(
            vehicleEntity(plate = "DOC001")
        ).toInt()
        val laterDocumentId = database.vehicleDocumentDao().insertDocument(
            documentEntity(
                vehicleId = vehicleId,
                type = VehicleDocumentType.SOAT,
                dueDate = "2026-08-20"
            )
        ).toInt()
        val earlierDocumentId = database.vehicleDocumentDao().insertDocument(
            documentEntity(
                vehicleId = vehicleId,
                type = VehicleDocumentType.TAXES,
                dueDate = "2026-07-25"
            )
        ).toInt()

        val orderedDocuments = database.vehicleDocumentDao()
            .getDocumentsByVehicle(vehicleId)
            .first()
        assertEquals(
            listOf(earlierDocumentId, laterDocumentId),
            orderedDocuments.map { it.id }
        )

        database.vehicleDocumentDao().updateDocument(
            documentEntity(
                id = earlierDocumentId,
                vehicleId = vehicleId,
                type = VehicleDocumentType.TAXES,
                dueDate = "2026-07-25",
                notes = "Pago programado"
            )
        )
        assertEquals(
            "Pago programado",
            database.vehicleDocumentDao().getDocumentsByVehicle(vehicleId).first().first().notes
        )

        database.vehicleDocumentDao().deleteDocument(
            database.vehicleDocumentDao().getDocumentsByVehicle(vehicleId).first().last()
        )
        assertEquals(
            1,
            database.vehicleDocumentDao().getDocumentsByVehicle(vehicleId).first().size
        )
    }

    private fun expenseEntity(
        id: Int = 0,
        vehicleId: Int,
        date: String,
        amount: Long
    ) = ExpenseEntity(
        id = id,
        vehicleId = vehicleId,
        date = date,
        category = ExpenseCategory.FUEL,
        amount = amount,
        description = "Combustible"
    )

    private fun noveltyEntity(
        id: Int = 0,
        vehicleId: Int
    ) = NoveltyEntity(
        id = id,
        vehicleId = vehicleId,
        date = "2026-07-15",
        type = "Ingreso diferente",
        description = "Operación parcial",
        priority = NoveltyPriority.MEDIUM,
        affectsIncome = true,
        incomeAdjustmentType = IncomeAdjustmentType.CUSTOM_AMOUNT,
        adjustedIncomeAmount = 125_000
    )

    private fun documentEntity(
        id: Int = 0,
        vehicleId: Int,
        type: VehicleDocumentType,
        dueDate: String,
        notes: String? = null
    ) = VehicleDocumentEntity(
        id = id,
        vehicleId = vehicleId,
        type = type,
        dueDate = dueDate,
        notes = notes
    )

    private fun vehicleEntity(plate: String) = VehicleEntity(
        plate = plate,
        brand = "Kia",
        model = "Picanto",
        type = VehicleType.TAXI,
        status = "Activo",
        currentDriver = "Conductor de prueba",
        dailyFixedIncome = 180_000
    )
}
