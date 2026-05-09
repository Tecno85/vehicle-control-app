package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDao
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toDomain
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VehicleLocalRepository(
    private val vehicleDao: VehicleDao
) {
    fun getAllVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAllVehicles().map { vehicles ->
            vehicles.map { vehicle -> vehicle.toDomain() }
        }
    }

    suspend fun getVehicleById(vehicleId: Int): Vehicle? {
        return vehicleDao.getVehicleById(vehicleId)?.toDomain()
    }

    suspend fun plateExists(plate: String): Boolean {
        return vehicleDao.plateExists(plate)
    }

    suspend fun insertVehicle(vehicle: Vehicle): Long {
        return vehicleDao.insertVehicle(vehicle.toEntity())
    }

    suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.updateVehicle(vehicle.toEntity())
    }

    suspend fun deleteVehicle(vehicle: Vehicle) {
        vehicleDao.deleteVehicle(vehicle.toEntity())
    }
}
