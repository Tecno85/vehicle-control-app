package com.ivanmadrid.vehiclecontrolapp.data.sample

import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

val sampleVehicles = listOf(
    Vehicle(
        id = 1,
        plate = "TLV144",
        brand = "Kia",
        model = "Picanto",
        type = VehicleType.TAXI,
        status = "Activo",
        currentDriver = "Jean Carlos Gámez",
        dailyFixedIncome = 180000L,
    ),
    Vehicle(
        id = 2,
        plate = "TLV871",
        brand = "Hyundai",
        model = "i10",
        type = VehicleType.TAXI,
        status = "Activo",
        currentDriver = "Ider Jimenez",
        dailyFixedIncome = 180000L,
    ),
    Vehicle(
        id = 3,
        plate = "DEF456",
        brand = "Mazda",
        model = "2",
        type = VehicleType.PRIVATE,
        status = "Activo",
        currentDriver = null,
        dailyFixedIncome = null,
    ),
    Vehicle(
        id = 4,
        plate = "GHI789",
        brand = "Toyota",
        model = "Corolla",
        type = VehicleType.PRIVATE,
        status = "Activo",
        currentDriver = null,
        dailyFixedIncome = null,
    ),
)
