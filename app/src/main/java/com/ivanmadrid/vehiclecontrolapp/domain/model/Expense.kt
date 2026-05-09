package com.ivanmadrid.vehiclecontrolapp.domain.model

data class Expense(
    val id: Int,
    val vehicleId: Int,
    val date: String,
    val category: ExpenseCategory,
    val amount: Long,
    val description: String,
)
