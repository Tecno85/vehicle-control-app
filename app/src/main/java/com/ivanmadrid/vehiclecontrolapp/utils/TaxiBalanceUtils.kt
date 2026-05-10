package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle

data class TaxiBalanceSummary(
    val baseIncome: Long,
    val adjustedIncome: Long,
    val totalExpenses: Long,
    val estimatedBalance: Long,
)

fun calculateTaxiBalanceSummary(
    vehicle: Vehicle,
    expenses: List<Expense>,
    novelties: List<Novelty>
): TaxiBalanceSummary {
    val baseIncome = vehicle.dailyFixedIncome ?: 0L
    val adjustedIncome = calculateAdjustedIncome(
        baseIncome = baseIncome,
        novelties = novelties
    )
    val totalExpenses = expenses.sumOf { expense ->
        expense.amount
    }

    return TaxiBalanceSummary(
        baseIncome = baseIncome,
        adjustedIncome = adjustedIncome,
        totalExpenses = totalExpenses,
        estimatedBalance = adjustedIncome - totalExpenses
    )
}

private fun calculateAdjustedIncome(
    baseIncome: Long,
    novelties: List<Novelty>
): Long {
    val incomeAffectingNovelties = novelties.filter { novelty ->
        novelty.affectsIncome
    }

    if (incomeAffectingNovelties.isEmpty()) {
        return baseIncome
    }

    return incomeAffectingNovelties.minOf { novelty ->
        when (novelty.incomeAdjustmentType) {
            IncomeAdjustmentType.NO_INCOME -> 0L
            IncomeAdjustmentType.HALF_INCOME -> baseIncome / 2
            IncomeAdjustmentType.CUSTOM_AMOUNT -> novelty.adjustedIncomeAmount ?: baseIncome
            null -> baseIncome
        }
    }
}
