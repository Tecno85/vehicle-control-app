package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import androidx.room.withTransaction
import com.ivanmadrid.vehiclecontrolapp.data.local.AppDatabase
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle

class VehicleDeletionRepository(
    private val database: AppDatabase
) {
    suspend fun deleteVehicleWithRelatedData(vehicle: Vehicle) {
        database.withTransaction {
            database.vehicleDocumentDao().deleteDocumentsByVehicle(vehicle.id)
            database.expenseDao().deleteExpensesByVehicle(vehicle.id)
            database.noveltyDao().deleteNoveltiesByVehicle(vehicle.id)
            database.vehicleDao().deleteVehicle(vehicle.toEntity())
        }
    }
}
