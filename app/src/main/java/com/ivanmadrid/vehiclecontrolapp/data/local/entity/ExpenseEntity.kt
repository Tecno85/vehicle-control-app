package com.ivanmadrid.vehiclecontrolapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val vehicleId: Int,
    val date: String,
    val category: ExpenseCategory,
    val amount: Long,
    val description: String,
)
