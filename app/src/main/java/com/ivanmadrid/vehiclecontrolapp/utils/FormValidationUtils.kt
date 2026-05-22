package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null
)

fun validateVehicleForm(
    plate: String,
    brand: String,
    model: String,
    vehicleType: VehicleType?,
    dailyFixedIncome: Long?
): ValidationResult {
    return when {
        plate.isBlank() -> invalidResult("Completa la placa del vehículo.")
        brand.isBlank() -> invalidResult("Completa la marca del vehículo.")
        model.isBlank() -> invalidResult("Completa el modelo del vehículo.")
        vehicleType == null -> invalidResult("Selecciona si el vehículo es taxi o particular.")
        vehicleType == VehicleType.TAXI && (dailyFixedIncome == null || dailyFixedIncome <= 0L) -> {
            invalidResult("Ingresa un ingreso diario mayor a cero para el taxi.")
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
        date.isBlank() -> invalidResult("Completa la fecha del gasto.")
        !isValidIsoDate(date.trim()) -> invalidResult("La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-05-09.")
        category == null -> invalidResult("Selecciona la categoría del gasto.")
        amount == null || amount <= 0L -> invalidResult("Ingresa un valor mayor a cero.")
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
        date.isBlank() -> invalidResult("Completa la fecha de la novedad.")
        !isValidIsoDate(date.trim()) -> invalidResult("La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-05-09.")
        noveltyType.isBlank() -> invalidResult("Completa el tipo de novedad.")
        priority == null -> invalidResult("Selecciona la prioridad de la novedad.")
        affectsIncome && incomeAdjustmentType == null -> invalidResult("Selecciona el resultado del día.")
        affectsIncome &&
            incomeAdjustmentType == IncomeAdjustmentType.CUSTOM_AMOUNT &&
            (adjustedIncomeAmount == null || adjustedIncomeAmount <= 0L) -> {
            invalidResult("Ingresa un ingreso real del día mayor a cero.")
        }
        else -> validResult()
    }
}

fun validateDocumentForm(
    documentType: VehicleDocumentType?,
    dueDate: String
): ValidationResult {
    return when {
        documentType == null -> invalidResult("Selecciona el tipo de documento.")
        dueDate.isBlank() -> invalidResult("Completa la fecha de vencimiento.")
        !isValidIsoDate(dueDate.trim()) -> invalidResult("La fecha debe tener el formato yyyy-MM-dd. Ej: 2026-05-09.")
        else -> validResult()
    }
}

private fun validResult(): ValidationResult {
    return ValidationResult(isValid = true)
}

private fun invalidResult(message: String): ValidationResult {
    return ValidationResult(isValid = false, message = message)
}
