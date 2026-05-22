package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FormValidationUtilsTest {
    @Test
    fun validateVehicleForm_rejectsTaxiWithoutPositiveDailyIncome() {
        val result = validateVehicleForm(
            plate = "ABC123",
            brand = "Kia",
            model = "Picanto",
            vehicleType = VehicleType.TAXI,
            dailyFixedIncome = 0L
        )

        assertFalse(result.isValid)
    }

    @Test
    fun validateVehicleForm_acceptsPrivateWithoutDailyIncome() {
        val result = validateVehicleForm(
            plate = "DEF456",
            brand = "Mazda",
            model = "3",
            vehicleType = VehicleType.PRIVATE,
            dailyFixedIncome = null
        )

        assertTrue(result.isValid)
    }

    @Test
    fun validateExpenseForm_rejectsInvalidDateAndZeroAmount() {
        val invalidDate = validateExpenseForm(
            date = "2026-99-99",
            category = ExpenseCategory.FUEL,
            amount = 50_000L
        )
        val zeroAmount = validateExpenseForm(
            date = "2026-05-09",
            category = ExpenseCategory.FUEL,
            amount = 0L
        )

        assertFalse(invalidDate.isValid)
        assertFalse(zeroAmount.isValid)
    }

    @Test
    fun validateNoveltyForm_requiresCustomIncomeAmountWhenSelected() {
        val result = validateNoveltyForm(
            date = "2026-05-09",
            noveltyType = "Trabajo parcial",
            priority = NoveltyPriority.MEDIUM,
            affectsIncome = true,
            incomeAdjustmentType = IncomeAdjustmentType.CUSTOM_AMOUNT,
            adjustedIncomeAmount = null
        )

        assertFalse(result.isValid)
    }

    @Test
    fun validateDocumentForm_rejectsMissingType() {
        val result = validateDocumentForm(
            documentType = null,
            dueDate = "2026-05-09"
        )

        assertFalse(result.isValid)
    }

    @Test
    fun validateDocumentForm_acceptsValidDocument() {
        val result = validateDocumentForm(
            documentType = VehicleDocumentType.SOAT,
            dueDate = "2026-05-09"
        )

        assertTrue(result.isValid)
    }
}
