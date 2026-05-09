package com.ivanmadrid.vehiclecontrolapp.domain.model

data class Novelty(
    val id: Int,
    val vehicleId: Int,
    val date: String,
    val type: String,
    val description: String,
    val priority: NoveltyPriority,
    val affectsIncome: Boolean,
    val incomeAdjustmentType: IncomeAdjustmentType?,
    val adjustedIncomeAmount: Long?,
)
