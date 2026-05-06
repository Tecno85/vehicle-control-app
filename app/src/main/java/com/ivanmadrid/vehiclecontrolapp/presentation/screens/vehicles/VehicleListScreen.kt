package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicles
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle

@Composable
fun VehicleListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Control Vehicular",
            style = MaterialTheme.typography.headlineMedium
        )

        sampleVehicles.forEach { vehicle ->
            VehicleCard(vehicle = vehicle)
        }
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = vehicle.plate,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${vehicle.brand} ${vehicle.model}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Tipo: ${getVehicleTypeLabel(vehicle.type)}")
            Text(text = "Estado: ${vehicle.status}")

            if (vehicle.type == VehicleType.TAXI) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Conductor: ${vehicle.currentDriver ?: "Sin asignar"}")
                Text(text = "Ingreso diario: $${vehicle.dailyFixedIncome ?: 0.0}")
            }
        }
    }
}

fun getVehicleTypeLabel(type: VehicleType): String {
    return when (type) {
        VehicleType.TAXI -> "Taxi"
        VehicleType.PRIVATE -> "Particular"
    }
}