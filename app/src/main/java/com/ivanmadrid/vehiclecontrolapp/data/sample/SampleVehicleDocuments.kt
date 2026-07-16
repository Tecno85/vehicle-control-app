package com.ivanmadrid.vehiclecontrolapp.data.sample

import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType

val sampleVehicleDocuments = listOf(
    VehicleDocument(
        id = 1,
        vehicleId = 1,
        type = VehicleDocumentType.SOAT,
        dueDate = "2026-08-05",
        notes = "Documento obligatorio del vehículo",
    ),
    VehicleDocument(
        id = 2,
        vehicleId = 4,
        type = VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW,
        dueDate = "2026-07-25",
        notes = "Revisión obligatoria del vehículo",
    ),
    VehicleDocument(
        id = 3,
        vehicleId = 2,
        type = VehicleDocumentType.TAXES,
        dueDate = "2026-08-20",
        notes = "Impuesto programado del taxi TLV871",
    ),
)
