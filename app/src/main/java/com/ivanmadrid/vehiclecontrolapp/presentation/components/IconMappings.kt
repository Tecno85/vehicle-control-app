package com.ivanmadrid.vehiclecontrolapp.presentation.components

import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType

fun getExpenseCategoryIcon(category: ExpenseCategory): Int {
    return when (category) {
        ExpenseCategory.FUEL -> R.drawable.ic_expense_fuel
        ExpenseCategory.WASH -> R.drawable.ic_expense_wash
        ExpenseCategory.MAINTENANCE -> R.drawable.ic_expense_maintenance
        ExpenseCategory.SPARE_PARTS -> R.drawable.ic_expense_spare_parts
        ExpenseCategory.INSURANCE -> R.drawable.ic_expense_insurance
        ExpenseCategory.TAXES -> R.drawable.ic_expense_taxes
        ExpenseCategory.FINES -> R.drawable.ic_expense_fine
        ExpenseCategory.OTHER -> R.drawable.ic_expense_other
    }
}

fun getVehicleDocumentIcon(type: VehicleDocumentType): Int {
    return when (type) {
        VehicleDocumentType.SOAT -> R.drawable.ic_doc_soat
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> R.drawable.ic_doc_technical
        VehicleDocumentType.TAXES -> R.drawable.ic_doc_taxes
    }
}
