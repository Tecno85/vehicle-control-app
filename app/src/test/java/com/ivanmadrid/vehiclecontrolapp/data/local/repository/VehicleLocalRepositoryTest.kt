package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDao
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VehicleLocalRepositoryTest {
    @Test
    fun getAllVehicles_mapsLocalEntitiesToDomainModels() = runBlocking {
        val dao = FakeVehicleDao(
            initialVehicles = listOf(vehicleEntity(id = 7, plate = "ABC123"))
        )
        val repository = VehicleLocalRepository(dao)

        val vehicles = repository.getAllVehicles().first()

        assertEquals(1, vehicles.size)
        assertEquals(7, vehicles.single().id)
        assertEquals("ABC123", vehicles.single().plate)
        assertEquals(VehicleType.TAXI, vehicles.single().type)
    }

    @Test
    fun insertAndUpdate_delegateMappedValuesToDao() = runBlocking {
        val dao = FakeVehicleDao()
        val repository = VehicleLocalRepository(dao)
        val newVehicle = vehicle(id = 0, plate = "NEW001")

        val insertedId = repository.insertVehicle(newVehicle)
        repository.updateVehicle(newVehicle.copy(id = insertedId.toInt(), status = "Taller"))

        assertEquals(1L, insertedId)
        assertEquals("NEW001", dao.insertedVehicle?.plate)
        assertEquals("Taller", dao.updatedVehicle?.status)
        assertTrue(repository.plateExists("NEW001"))
        assertFalse(repository.plateExistsForOtherVehicle("NEW001", insertedId.toInt()))
    }

    private fun vehicle(id: Int, plate: String) = Vehicle(
        id = id,
        plate = plate,
        brand = "Kia",
        model = "Picanto",
        type = VehicleType.TAXI,
        status = "Activo",
        currentDriver = "Conductor",
        dailyFixedIncome = 180_000
    )

    private fun vehicleEntity(id: Int, plate: String) = VehicleEntity(
        id = id,
        plate = plate,
        brand = "Kia",
        model = "Picanto",
        type = VehicleType.TAXI,
        status = "Activo",
        currentDriver = "Conductor",
        dailyFixedIncome = 180_000
    )
}

private class FakeVehicleDao(
    initialVehicles: List<VehicleEntity> = emptyList()
) : VehicleDao {
    private val vehicles = MutableStateFlow(initialVehicles)
    var insertedVehicle: VehicleEntity? = null
    var updatedVehicle: VehicleEntity? = null

    override fun getAllVehicles(): Flow<List<VehicleEntity>> = vehicles

    override fun getVehicleById(vehicleId: Int): Flow<VehicleEntity?> {
        return MutableStateFlow(vehicles.value.firstOrNull { it.id == vehicleId })
    }

    override suspend fun plateExists(plate: String): Boolean {
        return vehicles.value.any { it.plate.equals(plate, ignoreCase = true) } ||
            insertedVehicle?.plate.equals(plate, ignoreCase = true)
    }

    override suspend fun plateExistsForOtherVehicle(plate: String, vehicleId: Int): Boolean {
        return vehicles.value.any {
            it.id != vehicleId && it.plate.equals(plate, ignoreCase = true)
        }
    }

    override suspend fun getVehicleCount(): Int = vehicles.value.size

    override suspend fun insertVehicle(vehicle: VehicleEntity): Long {
        insertedVehicle = vehicle
        val id = 1
        vehicles.value = vehicles.value + vehicle.copy(id = id)
        return id.toLong()
    }

    override suspend fun updateVehicle(vehicle: VehicleEntity) {
        updatedVehicle = vehicle
        vehicles.value = vehicles.value.map { current ->
            if (current.id == vehicle.id) vehicle else current
        }
    }

    override suspend fun deleteVehicle(vehicle: VehicleEntity) {
        vehicles.value = vehicles.value.filterNot { it.id == vehicle.id }
    }
}
