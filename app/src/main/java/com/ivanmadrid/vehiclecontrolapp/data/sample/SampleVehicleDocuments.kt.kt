package com.ivanmadrid.vehiclecontrolapp.data.sample

import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType

val sampleVehicleDocuments = listOf(
    VehicleDocument(
        id = 1,
        vehicleId = 1,
        type = VehicleDocumentType.SOAT,
        dueDate = "2026-05-21",
        notes = "SOAT del taxi ABC123",
    ),
    VehicleDocument(
        id = 2,
        vehicleId = 4,
        type = VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW,
        dueDate = "2026-05-13",
        notes = "Revisión tecnicomecánica del vehículo GHI789",
    ),
    VehicleDocument(
        id = 3,
        vehicleId = 2,
        type = VehicleDocumentType.TAXES,
        dueDate = "2026-06-10",
        notes = "Impuesto pendiente del taxi XYZ789",
    ),
)