package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    onBackClick: () -> Unit,
    onRegisterExpenseClick: () -> Unit
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        OutlinedButton(
            onClick = onBackClick
        ) {
            Text(text = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        VehicleDetailHeaderCard(vehicle = vehicle)

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

        VehicleQuickActionsCard(
            onRegisterExpenseClick = onRegisterExpenseClick
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun VehicleDetailHeaderCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VehicleAvatar(type = vehicle.type)

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = vehicle.plate,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${vehicle.brand} ${vehicle.model}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    VehicleStatusChip(status = vehicle.status)
                }

                Spacer(modifier = Modifier.height(8.dp))

                VehicleTypeChip(type = vehicle.type)
            }
        }
    }
}
