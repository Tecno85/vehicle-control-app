package com.ivanmadrid.vehiclecontrolapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority

@Entity(tableName = "novelties")
data class NoveltyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val vehicleId: Int,
    val date: String,
    val type: String,
    val description: String,
    val priority: NoveltyPriority,
    val affectsIncome: Boolean,
    val incomeAdjustmentType: IncomeAdjustmentType?,
    val adjustedIncomeAmount: Long?,
)
