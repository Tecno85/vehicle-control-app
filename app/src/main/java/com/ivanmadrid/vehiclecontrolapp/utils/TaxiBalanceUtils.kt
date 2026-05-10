package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import java.time.LocalDate

data class TaxiBalanceSummary(
    val referenceDate: String?,
    val baseIncome: Long,
    val adjustedIncome: Long,
    val totalExpenses: Long,
    val estimatedBalance: Long,
)

fun calculateTaxiBalanceSummary(
    vehicle: Vehicle,
    expenses: List<Expense>,
    novelties: List<Novelty>,
    referenceDate: String? = null
): TaxiBalanceSummary {
    val selectedReferenceDate = referenceDate ?: findMostRecentDate(
        expenses = expenses,
        novelties = novelties
    )
    val dailyExpenses = expenses.filter { expense ->
        expense.date == selectedReferenceDate
    }
    val dailyNovelties = novelties.filter { novelty ->
        novelty.date == selectedReferenceDate
    }
    val baseIncome = vehicle.dailyFixedIncome ?: 0L
    val adjustedIncome = calculateAdjustedIncome(
        baseIncome = baseIncome,
        novelties = dailyNovelties
    )
    val totalExpenses = dailyExpenses.sumOf { expense ->
        expense.amount
    }

    return TaxiBalanceSummary(
        referenceDate = selectedReferenceDate,
        baseIncome = baseIncome,
        adjustedIncome = adjustedIncome,
        totalExpenses = totalExpenses,
        estimatedBalance = adjustedIncome - totalExpenses
    )
}

fun getTaxiBalanceReferenceDates(
    expenses: List<Expense>,
    novelties: List<Novelty>
): List<String> {
    return (expenses.map { expense -> expense.date } + novelties.map { novelty -> novelty.date })
        .mapNotNull { dateText -> parseLocalDateOrNull(dateText) }
        .distinct()
        .sortedDescending()
        .map { date -> date.toString() }
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

private fun findMostRecentDate(
    expenses: List<Expense>,
    novelties: List<Novelty>
): String? {
    return getTaxiBalanceReferenceDates(
        expenses = expenses,
        novelties = novelties
    ).firstOrNull()
}

private fun parseLocalDateOrNull(dateText: String): LocalDate? {
    return try {
        LocalDate.parse(dateText)
    } catch (_: Exception) {
        null
    }
}
