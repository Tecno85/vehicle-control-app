package com.ivanmadrid.vehiclecontrolapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType

@Entity(tableName = "vehicle_documents")
data class VehicleDocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val vehicleId: Int,
    val type: VehicleDocumentType,
    val dueDate: String,
    val notes: String?,
)
