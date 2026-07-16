package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null,
    val field: ValidationField? = null
)

enum class ValidationField {
    PLATE,
    BRAND,
    MODEL,
    VEHICLE_TYPE,
    DAILY_FIXED_INCOME,
    DATE,
    CATEGORY,
    AMOUNT,
    NOVELTY_TYPE,
    PRIORITY,
    INCOME_ADJUSTMENT_TYPE,
    ADJUSTED_INCOME_AMOUNT,
    DOCUMENT_TYPE,
}

fun validateVehicleForm(
    plate: String,
    brand: String,
    model: String,
    vehicleType: VehicleType?,
    dailyFixedIncome: Long?
): ValidationResult {
    return when {
        plate.isBlank() -> invalidResult("Completa la placa del vehículo.", ValidationField.PLATE)
        brand.isBlank() -> invalidResult("Completa la marca del vehículo.", ValidationField.BRAND)
        model.isBlank() -> invalidResult("Completa el modelo del vehículo.", ValidationField.MODEL)
        vehicleType == null -> invalidResult(
            "Selecciona si el vehículo es taxi o particular.",
            ValidationField.VEHICLE_TYPE
        )
        vehicleType == VehicleType.TAXI && (dailyFixedIncome == null || dailyFixedIncome <= 0L) -> {
            invalidResult(
                "Ingresa un ingreso diario mayor a cero para el taxi.",
                ValidationField.DAILY_FIXED_INCOME
            )
        }
        else -> validResult()
    }
}

fun validateExpenseForm(
    date: String,
    category: ExpenseCategory?,
    amount: Long?
): ValidationResult {
    return when {
        date.isBlank() -> invalidResult("Completa la fecha del gasto.", ValidationField.DATE)
        !isValidIsoDate(date.trim()) -> invalidResult(
            "La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-07-16.",
            ValidationField.DATE
        )
        category == null -> invalidResult("Selecciona la categoría del gasto.", ValidationField.CATEGORY)
        amount == null || amount <= 0L -> invalidResult("Ingresa un valor mayor a cero.", ValidationField.AMOUNT)
        else -> validResult()
    }
}

fun validateNoveltyForm(
    date: String,
    noveltyType: String,
    priority: NoveltyPriority?,
    affectsIncome: Boolean,
    incomeAdjustmentType: IncomeAdjustmentType?,
    adjustedIncomeAmount: Long?
): ValidationResult {
    return when {
        date.isBlank() -> invalidResult("Completa la fecha de la novedad.", ValidationField.DATE)
        !isValidIsoDate(date.trim()) -> invalidResult(
            "La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-07-16.",
            ValidationField.DATE
        )
        noveltyType.isBlank() -> invalidResult("Completa el tipo de novedad.", ValidationField.NOVELTY_TYPE)
        priority == null -> invalidResult("Selecciona la prioridad de la novedad.", ValidationField.PRIORITY)
        affectsIncome && incomeAdjustmentType == null -> invalidResult(
            "Selecciona el resultado del día.",
            ValidationField.INCOME_ADJUSTMENT_TYPE
        )
        affectsIncome &&
            incomeAdjustmentType == IncomeAdjustmentType.CUSTOM_AMOUNT &&
            (adjustedIncomeAmount == null || adjustedIncomeAmount <= 0L) -> {
            invalidResult(
                "Ingresa un ingreso real del día mayor a cero.",
                ValidationField.ADJUSTED_INCOME_AMOUNT
            )
        }
        else -> validResult()
    }
}

fun validateDocumentForm(
    documentType: VehicleDocumentType?,
    dueDate: String
): ValidationResult {
    return when {
        documentType == null -> invalidResult("Selecciona el tipo de documento.", ValidationField.DOCUMENT_TYPE)
        dueDate.isBlank() -> invalidResult("Completa la fecha de vencimiento.", ValidationField.DATE)
        !isValidIsoDate(dueDate.trim()) -> invalidResult(
            "La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-07-16.",
            ValidationField.DATE
        )
        else -> validResult()
    }
}

private fun validResult(): ValidationResult {
    return ValidationResult(isValid = true)
}

private fun invalidResult(message: String, field: ValidationField): ValidationResult {
    return ValidationResult(isValid = false, message = message, field = field)
}
