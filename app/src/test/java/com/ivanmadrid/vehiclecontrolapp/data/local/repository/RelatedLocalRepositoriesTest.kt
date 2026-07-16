package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.ExpenseDao
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.NoveltyDao
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDocumentDao
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RelatedLocalRepositoriesTest {
    @Test
    fun expenseRepository_mapsAndDelegatesCrud() = runBlocking {
        val dao = FakeExpenseDao()
        val repository = ExpenseLocalRepository(dao)
        val expense = Expense(
            id = 0,
            vehicleId = 1,
            date = "2026-07-15",
            category = ExpenseCategory.MAINTENANCE,
            amount = 95_000,
            description = "Cambio de aceite"
        )

        val id = repository.insertExpense(expense).toInt()
        assertEquals(ExpenseCategory.MAINTENANCE, repository.getExpensesByVehicle(1).first().single().category)

        repository.updateExpense(expense.copy(id = id, amount = 100_000))
        assertEquals(100_000L, repository.getExpensesByVehicle(1).first().single().amount)

        repository.deleteExpense(expense.copy(id = id))
        assertTrue(repository.getExpensesByVehicle(1).first().isEmpty())
    }

    @Test
    fun noveltyRepository_preservesIncomeAdjustmentAndDelegatesCrud() = runBlocking {
        val dao = FakeNoveltyDao()
        val repository = NoveltyLocalRepository(dao)
        val novelty = Novelty(
            id = 0,
            vehicleId = 1,
            date = "2026-07-15",
            type = "Trabajo parcial",
            description = "Ingreso real registrado",
            priority = NoveltyPriority.MEDIUM,
            affectsIncome = true,
            incomeAdjustmentType = IncomeAdjustmentType.CUSTOM_AMOUNT,
            adjustedIncomeAmount = 120_000
        )

        val id = repository.insertNovelty(novelty).toInt()
        val storedNovelty = repository.getNoveltiesByVehicle(1).first().single()
        assertEquals(IncomeAdjustmentType.CUSTOM_AMOUNT, storedNovelty.incomeAdjustmentType)
        assertEquals(120_000L, storedNovelty.adjustedIncomeAmount)

        repository.updateNovelty(novelty.copy(id = id, priority = NoveltyPriority.HIGH))
        assertEquals(NoveltyPriority.HIGH, repository.getNoveltiesByVehicle(1).first().single().priority)

        repository.deleteNovelty(novelty.copy(id = id))
        assertTrue(repository.getNoveltiesByVehicle(1).first().isEmpty())
    }

    @Test
    fun documentRepository_mapsAllDocumentsAndDelegatesCrud() = runBlocking {
        val dao = FakeVehicleDocumentDao()
        val repository = VehicleDocumentLocalRepository(dao)
        val document = VehicleDocument(
            id = 0,
            vehicleId = 1,
            type = VehicleDocumentType.SOAT,
            dueDate = "2026-08-05",
            notes = null
        )

        val id = repository.insertDocument(document).toInt()
        assertEquals(VehicleDocumentType.SOAT, repository.getAllDocumentsByDueDate().first().single().type)

        repository.updateDocument(document.copy(id = id, notes = "Renovación programada"))
        assertEquals(
            "Renovación programada",
            repository.getDocumentsByVehicle(1).first().single().notes
        )

        repository.deleteDocument(document.copy(id = id))
        assertTrue(repository.getDocumentsByVehicle(1).first().isEmpty())
    }
}

private class FakeExpenseDao : ExpenseDao {
    private val expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())

    override fun getExpensesByVehicle(vehicleId: Int): Flow<List<ExpenseEntity>> {
        return MutableStateFlow(expenses.value.filter { it.vehicleId == vehicleId })
    }

    override suspend fun insertExpense(expense: ExpenseEntity): Long {
        val id = 1
        expenses.value += expense.copy(id = id)
        return id.toLong()
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        expenses.value = expenses.value.map { if (it.id == expense.id) expense else it }
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        expenses.value = expenses.value.filterNot { it.id == expense.id }
    }

    override suspend fun deleteExpensesByVehicle(vehicleId: Int) {
        expenses.value = expenses.value.filterNot { it.vehicleId == vehicleId }
    }
}

private class FakeNoveltyDao : NoveltyDao {
    private val novelties = MutableStateFlow<List<NoveltyEntity>>(emptyList())

    override fun getNoveltiesByVehicle(vehicleId: Int): Flow<List<NoveltyEntity>> {
        return MutableStateFlow(novelties.value.filter { it.vehicleId == vehicleId })
    }

    override suspend fun insertNovelty(novelty: NoveltyEntity): Long {
        val id = 1
        novelties.value += novelty.copy(id = id)
        return id.toLong()
    }

    override suspend fun updateNovelty(novelty: NoveltyEntity) {
        novelties.value = novelties.value.map { if (it.id == novelty.id) novelty else it }
    }

    override suspend fun deleteNovelty(novelty: NoveltyEntity) {
        novelties.value = novelties.value.filterNot { it.id == novelty.id }
    }

    override suspend fun deleteNoveltiesByVehicle(vehicleId: Int) {
        novelties.value = novelties.value.filterNot { it.vehicleId == vehicleId }
    }
}

private class FakeVehicleDocumentDao : VehicleDocumentDao {
    private val documents = MutableStateFlow<List<VehicleDocumentEntity>>(emptyList())

    override fun getDocumentsByVehicle(vehicleId: Int): Flow<List<VehicleDocumentEntity>> {
        return MutableStateFlow(documents.value.filter { it.vehicleId == vehicleId })
    }

    override fun getAllDocumentsByDueDate(): Flow<List<VehicleDocumentEntity>> = documents

    override suspend fun insertDocument(document: VehicleDocumentEntity): Long {
        val id = 1
        documents.value += document.copy(id = id)
        return id.toLong()
    }

    override suspend fun updateDocument(document: VehicleDocumentEntity) {
        documents.value = documents.value.map { if (it.id == document.id) document else it }
    }

    override suspend fun deleteDocument(document: VehicleDocumentEntity) {
        documents.value = documents.value.filterNot { it.id == document.id }
    }

    override suspend fun deleteDocumentsByVehicle(vehicleId: Int) {
        documents.value = documents.value.filterNot { it.vehicleId == vehicleId }
    }
}
