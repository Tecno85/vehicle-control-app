package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

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
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = vehicle.plate)
            Text(text = "${vehicle.brand} ${vehicle.model}")
            Text(text = "Tipo: ${vehicle.type}")
            Text(text = "Estado: ${vehicle.status}")
        }
    }
}