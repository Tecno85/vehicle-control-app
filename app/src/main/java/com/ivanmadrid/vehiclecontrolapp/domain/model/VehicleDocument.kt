package com.ivanmadrid.vehiclecontrolapp.domain.model

data class VehicleDocument(
    val id: Int,
    val vehicleId: Int,
    val type: VehicleDocumentType,
    val dueDate: String,
    val notes: String?,
)