package com.ivanmadrid.vehiclecontrolapp.data.local.mapper

import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalMappersTest {
    @Test
    fun vehicleEntityToDomain_mapsAllFields() {
        val entity = VehicleEntity(
            id = 1,
            plate = "ABC123",
            brand = "Kia",
            model = "Picanto",
            type = VehicleType.TAXI,
            status = "Activo",
            currentDriver = "Carlos Pérez",
            dailyFixedIncome = 180000L,
        )

        val vehicle = entity.toDomain()

        assertEquals(entity.id, vehicle.id)
        assertEquals(entity.plate, vehicle.plate)
        assertEquals(entity.brand, vehicle.brand)
        assertEquals(entity.model, vehicle.model)
        assertEquals(entity.type, vehicle.type)
        assertEquals(entity.status, vehicle.status)
        assertEquals(entity.currentDriver, vehicle.currentDriver)
        assertEquals(entity.dailyFixedIncome, vehicle.dailyFixedIncome)
    }

    @Test
    fun expenseEntityToDomain_mapsAllFields() {
        val entity = ExpenseEntity(
            id = 1,
            vehicleId = 1,
            date = "2026-05-09",
            category = ExpenseCategory.FUEL,
            amount = 65000L,
            description = "Tanqueo diario",
        )

        val expense = entity.toDomain()

        assertEquals(entity.id, expense.id)
        assertEquals(entity.vehicleId, expense.vehicleId)
        assertEquals(entity.date, expense.date)
        assertEquals(entity.category, expense.category)
        assertEquals(entity.amount, expense.amount)
        assertEquals(entity.description, expense.description)
    }

    @Test
    fun noveltyEntityToDomain_mapsAllFields() {
        val entity = NoveltyEntity(
            id = 1,
            vehicleId = 1,
            date = "2026-05-09",
            type = "Trabajo parcial",
            description = "El taxi trabajó medio día.",
            priority = NoveltyPriority.MEDIUM,
            affectsIncome = true,
            incomeAdjustmentType = IncomeAdjustmentType.HALF_INCOME,
            adjustedIncomeAmount = null,
        )

        val novelty = entity.toDomain()

        assertEquals(entity.id, novelty.id)
        assertEquals(entity.vehicleId, novelty.vehicleId)
        assertEquals(entity.date, novelty.date)
        assertEquals(entity.type, novelty.type)
        assertEquals(entity.description, novelty.description)
        assertEquals(entity.priority, novelty.priority)
        assertEquals(entity.affectsIncome, novelty.affectsIncome)
        assertEquals(entity.incomeAdjustmentType, novelty.incomeAdjustmentType)
        assertEquals(entity.adjustedIncomeAmount, novelty.adjustedIncomeAmount)
    }

    @Test
    fun vehicleDocumentEntityToDomain_mapsAllFields() {
        val entity = VehicleDocumentEntity(
            id = 1,
            vehicleId = 1,
            type = VehicleDocumentType.SOAT,
            dueDate = "2026-05-21",
            notes = "Documento obligatorio",
        )

        val document = entity.toDomain()

        assertEquals(entity.id, document.id)
        assertEquals(entity.vehicleId, document.vehicleId)
        assertEquals(entity.type, document.type)
        assertEquals(entity.dueDate, document.dueDate)
        assertEquals(entity.notes, document.notes)
    }
}
