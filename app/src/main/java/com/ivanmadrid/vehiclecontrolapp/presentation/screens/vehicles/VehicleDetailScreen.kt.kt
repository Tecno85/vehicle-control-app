package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

@Composable
fun VehicleDetailScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = vehicle.plate,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "${vehicle.brand} ${vehicle.model}")
        Text(text = "Tipo: ${getVehicleTypeLabel(vehicle.type)}")
        Text(text = "Estado: ${vehicle.status}")

        if (vehicle.type == VehicleType.TAXI) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Información del taxi",
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "Conductor: ${vehicle.currentDriver ?: "Sin asignar"}")
            Text(text = "Ingreso diario: $${vehicle.dailyFixedIncome ?: 0.0}")
        }
    }
}