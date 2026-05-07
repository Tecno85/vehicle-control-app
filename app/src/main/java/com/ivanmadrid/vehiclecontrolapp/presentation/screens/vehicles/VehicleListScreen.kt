package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.clickable
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicleDocuments
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicles
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import java.util.Locale

@Composable
fun VehicleListScreen(
    modifier: Modifier = Modifier,
    onVehicleClick: (Vehicle) -> Unit
) {
    val taxiCount = sampleVehicles.count { vehicle ->
        vehicle.type == VehicleType.TAXI
    }

    val privateCount = sampleVehicles.count { vehicle ->
        vehicle.type == VehicleType.PRIVATE
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Control Vehicular",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${sampleVehicles.size} vehículos registrados",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryChip(text = "$taxiCount taxis")
            SummaryChip(text = "$privateCount particulares")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Próximos vencimientos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        sampleVehicleDocuments.take(2).forEach { document ->
            DocumentReminderCard(document = document)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Vehículos registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        sampleVehicles.forEach { vehicle ->
            VehicleCard(
                vehicle = vehicle,
                onClick = {
                    onVehicleClick(vehicle)
                }
            )
        }
    }
}

@Composable
fun SummaryChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun DocumentReminderCard(document: VehicleDocument) {
    val vehicle = sampleVehicles.firstOrNull { vehicle ->
        vehicle.id == document.vehicleId
    }

    val vehiclePlate = vehicle?.plate ?: "Vehículo"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "${getDocumentTypeLabel(document.type)} $vehiclePlate",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Vence el ${document.dueDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            if (!document.notes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = document.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}
@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = vehicle.plate,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${vehicle.brand} ${vehicle.model}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                VehicleStatusChip(status = vehicle.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            VehicleTypeChip(type = vehicle.type)

            if (vehicle.type == VehicleType.TAXI) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem(
                        label = "Conductor",
                        value = vehicle.currentDriver ?: "Sin asignar",
                        modifier = Modifier.weight(1f)
                    )

                    InfoItem(
                        label = "Ingreso diario",
                        value = formatCurrency(vehicle.dailyFixedIncome),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun VehicleTypeChip(type: VehicleType) {
    val label = getVehicleTypeLabel(type)

    val containerColor = when (type) {
        VehicleType.TAXI -> MaterialTheme.colorScheme.tertiaryContainer
        VehicleType.PRIVATE -> MaterialTheme.colorScheme.secondaryContainer
    }

    val contentColor = when (type) {
        VehicleType.TAXI -> MaterialTheme.colorScheme.onTertiaryContainer
        VehicleType.PRIVATE -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun VehicleStatusChip(status: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun getVehicleTypeLabel(type: VehicleType): String {
    return when (type) {
        VehicleType.TAXI -> "Taxi"
        VehicleType.PRIVATE -> "Particular"
    }
}

fun getDocumentTypeLabel(type: VehicleDocumentType): String {
    return when (type) {
        VehicleDocumentType.SOAT -> "SOAT"
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> "Tecnicomecánica"
        VehicleDocumentType.TAXES -> "Impuestos"
    }
}

fun formatCurrency(value: Double?): String {
    val amount = value ?: 0.0
    return "$" + "%,.0f".format(Locale.US, amount).replace(",", ".")
}