package com.ivanmadrid.vehiclecontrolapp.data.local.mapper

import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument

fun VehicleEntity.toDomain(): Vehicle {
    return Vehicle(
        id = id,
        plate = plate,
        brand = brand,
        model = model,
        type = type,
        status = status,
        currentDriver = currentDriver,
        dailyFixedIncome = dailyFixedIncome,
    )
}

fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = id,
        plate = plate,
        brand = brand,
        model = model,
        type = type,
        status = status,
        currentDriver = currentDriver,
        dailyFixedIncome = dailyFixedIncome,
    )
}

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = id,
        vehicleId = vehicleId,
        date = date,
        category = category,
        amount = amount,
        description = description,
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        vehicleId = vehicleId,
        date = date,
        category = category,
        amount = amount,
        description = description,
    )
}

fun NoveltyEntity.toDomain(): Novelty {
    return Novelty(
        id = id,
        vehicleId = vehicleId,
        date = date,
        type = type,
        description = description,
        priority = priority,
        affectsIncome = affectsIncome,
        incomeAdjustmentType = incomeAdjustmentType,
        adjustedIncomeAmount = adjustedIncomeAmount,
    )
}

fun Novelty.toEntity(): NoveltyEntity {
    return NoveltyEntity(
        id = id,
        vehicleId = vehicleId,
        date = date,
        type = type,
        description = description,
        priority = priority,
        affectsIncome = affectsIncome,
        incomeAdjustmentType = incomeAdjustmentType,
        adjustedIncomeAmount = adjustedIncomeAmount,
    )
}

fun VehicleDocumentEntity.toDomain(): VehicleDocument {
    return VehicleDocument(
        id = id,
        vehicleId = vehicleId,
        type = type,
        dueDate = dueDate,
        notes = notes,
    )
}

fun VehicleDocument.toEntity(): VehicleDocumentEntity {
    return VehicleDocumentEntity(
        id = id,
        vehicleId = vehicleId,
        type = type,
        dueDate = dueDate,
        notes = notes,
    )
}
