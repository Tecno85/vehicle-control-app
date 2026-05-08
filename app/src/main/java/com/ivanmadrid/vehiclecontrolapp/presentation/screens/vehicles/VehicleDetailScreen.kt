package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleExpenses
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleNovelties
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicleDocuments
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

@Composable
fun VehicleDetailScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val vehicleDocuments = sampleVehicleDocuments.filter { document ->
        document.vehicleId == vehicle.id
    }

    val vehicleExpenses = sampleExpenses.filter { expense ->
        expense.vehicleId == vehicle.id
    }

    val vehicleNovelties = sampleNovelties.filter { novelty ->
        novelty.vehicleId == vehicle.id
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(
            onClick = onBackClick
        ) {
            Text(text = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = vehicle.plate,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "${vehicle.brand} ${vehicle.model}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        VehicleGeneralInfoCard(vehicle = vehicle)

        if (vehicle.type == VehicleType.TAXI) {
            Spacer(modifier = Modifier.height(12.dp))
            TaxiInfoCard(vehicle = vehicle)
        }

        Spacer(modifier = Modifier.height(12.dp))

        VehicleDocumentsCard(documents = vehicleDocuments)

        Spacer(modifier = Modifier.height(12.dp))

        VehicleExpensesCard(expenses = vehicleExpenses)

        Spacer(modifier = Modifier.height(12.dp))

        VehicleNoveltiesCard(novelties = vehicleNovelties)

        Spacer(modifier = Modifier.height(12.dp))

        VehicleQuickActionsCard()
    }
}