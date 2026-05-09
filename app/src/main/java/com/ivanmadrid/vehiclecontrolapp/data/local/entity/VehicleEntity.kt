package com.ivanmadrid.vehiclecontrolapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plate: String,
    val brand: String,
    val model: String,
    val type: VehicleType,
    val status: String,
    val currentDriver: String?,
    val dailyFixedIncome: Long?,
)
