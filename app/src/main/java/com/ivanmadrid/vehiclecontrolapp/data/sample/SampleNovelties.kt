package com.ivanmadrid.vehiclecontrolapp.data.sample

import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority

val sampleNovelties = listOf(
    Novelty(
        id = 1,
        vehicleId = 1,
        date = "2026-07-15",
        type = "Trabajo parcial",
        description = "El taxi trabajó solo medio día por mantenimiento menor.",
        priority = NoveltyPriority.MEDIUM,
        affectsIncome = true,
        incomeAdjustmentType = IncomeAdjustmentType.HALF_INCOME,
        adjustedIncomeAmount = null,
    ),
    Novelty(
        id = 2,
        vehicleId = 2,
        date = "2026-07-14",
        type = "Conductor enfermo",
        description = "El conductor no pudo laborar durante el día.",
        priority = NoveltyPriority.HIGH,
        affectsIncome = true,
        incomeAdjustmentType = IncomeAdjustmentType.NO_INCOME,
        adjustedIncomeAmount = null,
    ),
    Novelty(
        id = 3,
        vehicleId = 3,
        date = "2026-07-13",
        type = "Observación general",
        description = "Se recomienda revisar presión de las llantas.",
        priority = NoveltyPriority.LOW,
        affectsIncome = false,
        incomeAdjustmentType = null,
        adjustedIncomeAmount = null,
    ),
    Novelty(
        id = 4,
        vehicleId = 4,
        date = "2026-07-12",
        type = "Documento pendiente",
        description = "Verificar fecha de pago de impuestos.",
        priority = NoveltyPriority.MEDIUM,
        affectsIncome = false,
        incomeAdjustmentType = null,
        adjustedIncomeAmount = null,
    ),
)
