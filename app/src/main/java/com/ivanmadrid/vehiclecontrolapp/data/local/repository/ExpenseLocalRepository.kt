package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.ExpenseDao
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toDomain
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseLocalRepository(
    private val expenseDao: ExpenseDao
) {
    fun getExpensesByVehicle(vehicleId: Int): Flow<List<Expense>> {
        return expenseDao.getExpensesByVehicle(vehicleId).map { expenses ->
            expenses.map { expense -> expense.toDomain() }
        }
    }

    suspend fun insertExpense(expense: Expense): Long {
        return expenseDao.insertExpense(expense.toEntity())
    }

    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense.toEntity())
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }
}
