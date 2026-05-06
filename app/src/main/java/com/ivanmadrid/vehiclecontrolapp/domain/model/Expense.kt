package com.ivanmadrid.vehiclecontrolapp.domain.model

data class Expense(
    val id: Int,
    val vehicleId: Int,
    val date: String,
    val category: ExpenseCategory,
    val amount: Double,
    val description: String,
)