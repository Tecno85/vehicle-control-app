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
