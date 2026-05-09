package com.ivanmadrid.vehiclecontrolapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDocumentDao {
    @Query("SELECT * FROM vehicle_documents WHERE vehicleId = :vehicleId ORDER BY dueDate ASC")
    fun getDocumentsByVehicle(vehicleId: Int): Flow<List<VehicleDocumentEntity>>

    @Query("SELECT * FROM vehicle_documents ORDER BY dueDate ASC")
    fun getAllDocumentsByDueDate(): Flow<List<VehicleDocumentEntity>>

    @Insert
    suspend fun insertDocument(document: VehicleDocumentEntity): Long

    @Update
    suspend fun updateDocument(document: VehicleDocumentEntity)

    @Delete
    suspend fun deleteDocument(document: VehicleDocumentEntity)

    @Query("DELETE FROM vehicle_documents WHERE vehicleId = :vehicleId")
    suspend fun deleteDocumentsByVehicle(vehicleId: Int)
}
