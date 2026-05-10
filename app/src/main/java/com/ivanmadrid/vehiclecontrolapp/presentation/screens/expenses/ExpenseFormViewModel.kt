package com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.ExpenseLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import kotlinx.coroutines.launch

class ExpenseFormViewModel(
    private val expenseRepository: ExpenseLocalRepository
) : ViewModel() {
    fun saveExpense(
        expense: Expense,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            if (expense.id == 0) {
                expenseRepository.insertExpense(expense)
            } else {
                expenseRepository.updateExpense(expense)
            }

            onSaved()
        }
    }

    class Factory(
        private val expenseRepository: ExpenseLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExpenseFormViewModel::class.java)) {
                return ExpenseFormViewModel(
                    expenseRepository = expenseRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
