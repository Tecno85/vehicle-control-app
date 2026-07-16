package com.ivanmadrid.vehiclecontrolapp.data.sample

import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory

val sampleExpenses = listOf(
    Expense(
        id = 1,
        vehicleId = 1,
        date = "2026-07-15",
        category = ExpenseCategory.FUEL,
        amount = 65000L,
        description = "Tanqueo diario",
    ),
    Expense(
        id = 2,
        vehicleId = 1,
        date = "2026-07-15",
        category = ExpenseCategory.WASH,
        amount = 15000L,
        description = "Lavado exterior",
    ),
    Expense(
        id = 3,
        vehicleId = 2,
        date = "2026-07-14",
        category = ExpenseCategory.MAINTENANCE,
        amount = 120000L,
        description = "Cambio de aceite",
    ),
    Expense(
        id = 4,
        vehicleId = 3,
        date = "2026-07-13",
        category = ExpenseCategory.FUEL,
        amount = 80000L,
        description = "Combustible vehículo particular",
    ),
    Expense(
        id = 5,
        vehicleId = 4,
        date = "2026-07-12",
        category = ExpenseCategory.INSURANCE,
        amount = 350000L,
        description = "Pago de seguro",
    ),
)
