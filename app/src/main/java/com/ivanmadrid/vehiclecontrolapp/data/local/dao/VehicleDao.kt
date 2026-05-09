package com.ivanmadrid.vehiclecontrolapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY plate ASC")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :vehicleId LIMIT 1")
    fun getVehicleById(vehicleId: Int): Flow<VehicleEntity?>

    @Query("SELECT COUNT(*) > 0 FROM vehicles WHERE UPPER(plate) = UPPER(:plate) LIMIT 1")
    suspend fun plateExists(plate: String): Boolean

    @Query("SELECT COUNT(*) > 0 FROM vehicles WHERE UPPER(plate) = UPPER(:plate) AND id != :vehicleId LIMIT 1")
    suspend fun plateExistsForOtherVehicle(
        plate: String,
        vehicleId: Int
    ): Boolean

    @Query("SELECT COUNT(*) FROM vehicles")
    suspend fun getVehicleCount(): Int

    @Insert
    suspend fun insertVehicle(vehicle: VehicleEntity): Long

    @Update
    suspend fun updateVehicle(vehicle: VehicleEntity)

    @Delete
    suspend fun deleteVehicle(vehicle: VehicleEntity)
}
