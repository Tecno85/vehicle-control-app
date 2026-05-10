package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import org.junit.Assert.assertEquals
import org.junit.Test

class TaxiBalanceUtilsTest {
    @Test
    fun calculateTaxiBalanceSummary_usesBaseIncomeWhenThereAreNoIncomeNovelties() {
        val summary = calculateTaxiBalanceSummary(
            vehicle = testTaxi(dailyFixedIncome = 180_000),
            expenses = listOf(testExpense(amount = 50_000)),
            novelties = emptyList()
        )

        assertEquals(180_000, summary.baseIncome)
        assertEquals(180_000, summary.adjustedIncome)
        assertEquals(50_000, summary.totalExpenses)
        assertEquals(130_000, summary.estimatedBalance)
    }

    @Test
    fun calculateTaxiBalanceSummary_appliesNoIncomeNovelty() {
        val summary = calculateTaxiBalanceSummary(
            vehicle = testTaxi(dailyFixedIncome = 180_000),
            expenses = listOf(testExpense(amount = 20_000)),
            novelties = listOf(
                testNovelty(
                    incomeAdjustmentType = IncomeAdjustmentType.NO_INCOME
                )
            )
        )

        assertEquals(0, summary.adjustedIncome)
        assertEquals(-20_000, summary.estimatedBalance)
    }

    @Test
    fun calculateTaxiBalanceSummary_appliesHalfIncomeNovelty() {
        val summary = calculateTaxiBalanceSummary(
            vehicle = testTaxi(dailyFixedIncome = 180_000),
            expenses = listOf(testExpense(amount = 30_000)),
            novelties = listOf(
                testNovelty(
                    incomeAdjustmentType = IncomeAdjustmentType.HALF_INCOME
                )
            )
        )

        assertEquals(90_000, summary.adjustedIncome)
        assertEquals(60_000, summary.estimatedBalance)
    }

    @Test
    fun calculateTaxiBalanceSummary_appliesCustomIncomeNovelty() {
        val summary = calculateTaxiBalanceSummary(
            vehicle = testTaxi(dailyFixedIncome = 180_000),
            expenses = listOf(testExpense(amount = 15_000)),
            novelties = listOf(
                testNovelty(
                    incomeAdjustmentType = IncomeAdjustmentType.CUSTOM_AMOUNT,
                    adjustedIncomeAmount = 120_000
                )
            )
        )

        assertEquals(120_000, summary.adjustedIncome)
        assertEquals(105_000, summary.estimatedBalance)
    }

    private fun testTaxi(dailyFixedIncome: Long): Vehicle {
        return Vehicle(
            id = 1,
            plate = "ABC123",
            brand = "Kia",
            model = "Picanto",
            type = VehicleType.TAXI,
            status = "Activo",
            currentDriver = "Carlos Perez",
            dailyFixedIncome = dailyFixedIncome,
        )
    }

    private fun testExpense(amount: Long): Expense {
        return Expense(
            id = 1,
            vehicleId = 1,
            date = "2026-05-09",
            category = ExpenseCategory.FUEL,
            amount = amount,
            description = "Tanqueo",
        )
    }

    private fun testNovelty(
        incomeAdjustmentType: IncomeAdjustmentType,
        adjustedIncomeAmount: Long? = null
    ): Novelty {
        return Novelty(
            id = 1,
            vehicleId = 1,
            date = "2026-05-09",
            type = "Trabajo parcial",
            description = "Afecta el ingreso",
            priority = NoveltyPriority.MEDIUM,
            affectsIncome = true,
            incomeAdjustmentType = incomeAdjustmentType,
            adjustedIncomeAmount = adjustedIncomeAmount,
        )
    }
}
