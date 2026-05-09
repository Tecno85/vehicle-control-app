package com.ivanmadrid.vehiclecontrolapp.domain.model

data class Vehicle(
    val id: Int,
    val plate: String,
    val brand: String,
    val model: String,
    val type: VehicleType,
    val status: String,
    val currentDriver: String?,
    val dailyFixedIncome: Long?,
)
