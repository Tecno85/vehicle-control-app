package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicleDocuments
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilLabel
import com.ivanmadrid.vehiclecontrolapp.utils.sortDocumentsByDueDate
import java.util.Locale

private val TaxiBlue = Color(0xFF0B63CE)
private val PrivateGreen = Color(0xFF188038)
private val WarningOrange = Color(0xFFE8710A)
private val AlertRed = Color(0xFFD93025)
private val SoftBlue = Color(0xFFE8F1FF)
private val SoftGreen = Color(0xFFE8F5E9)
private val SoftYellow = Color(0xFFFFF7DB)
private val SoftRed = Color(0xFFFFECEC)
private val DividerColor = Color(0xFFE4E7EC)

@Composable
fun VehicleListScreen(
    vehicles: List<Vehicle>,
    modifier: Modifier = Modifier,
    onVehicleClick: (Vehicle) -> Unit
) {
    val taxiCount = vehicles.count { vehicle ->
        vehicle.type == VehicleType.TAXI
    }

    val privateCount = vehicles.count { vehicle ->
        vehicle.type == VehicleType.PRIVATE
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Text(
            text = "Control Vehicular",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "${vehicles.size} vehículos registrados",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryChip(
                    text = "$taxiCount taxis",
                    type = VehicleType.TAXI
                )
                SummaryChip(
                    text = "$privateCount particulares",
                    type = VehicleType.PRIVATE
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(title = "Próximos vencimientos")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sortDocumentsByDueDate(sampleVehicleDocuments).take(2).forEachIndexed { index, document ->
                DocumentReminderCard(
                    document = document,
                    vehicles = vehicles,
                    isUrgent = index == 0,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Vehículos registrados")

        Spacer(modifier = Modifier.height(4.dp))

        vehicles.forEach { vehicle ->
            VehicleCard(
                vehicle = vehicle,
                onClick = {
                    onVehicleClick(vehicle)
                }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SummaryChip(
    text: String,
    type: VehicleType
) {
    val chipColor = when (type) {
        VehicleType.TAXI -> TaxiBlue
        VehicleType.PRIVATE -> PrivateGreen
    }

    val chipBackground = when (type) {
        VehicleType.TAXI -> SoftBlue
        VehicleType.PRIVATE -> SoftGreen
    }

    Surface(
        color = chipBackground,
        contentColor = chipColor,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DocumentReminderCard(
    document: VehicleDocument,
    vehicles: List<Vehicle>,
    isUrgent: Boolean,
    modifier: Modifier = Modifier
) {
    val vehicle = vehicles.firstOrNull { vehicle ->
        vehicle.id == document.vehicleId
    }

    val vehiclePlate = vehicle?.plate ?: "Vehículo"
    val accentColor = if (isUrgent) AlertRed else WarningOrange
    val containerColor = if (isUrgent) SoftRed else SoftYellow

    Card(
        modifier = modifier.padding(top = 8.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = document.type.shortLabel(),
                    style = MaterialTheme.typography.labelSmall,
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${getDocumentTypeLabel(document.type)} $vehiclePlate",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${getDaysUntilLabel(document.dueDate)} · ${document.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
            .padding(top = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
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
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${vehicle.brand} ${vehicle.model}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    VehicleStatusChip(status = vehicle.status)
                }

                Spacer(modifier = Modifier.height(8.dp))

                VehicleTypeChip(type = vehicle.type)

                if (vehicle.type == VehicleType.TAXI) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = DividerColor
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

                        Spacer(modifier = Modifier.width(12.dp))

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
}

@Composable
fun VehicleAvatar(type: VehicleType) {
    val backgroundColor = when (type) {
        VehicleType.TAXI -> SoftYellow
        VehicleType.PRIVATE -> SoftBlue
    }

    val textColor = when (type) {
        VehicleType.TAXI -> WarningOrange
        VehicleType.PRIVATE -> TaxiBlue
    }

    val label = when (type) {
        VehicleType.TAXI -> "TAXI"
        VehicleType.PRIVATE -> "AUTO"
    }

    Box(
        modifier = Modifier
            .size(84.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun VehicleTypeChip(type: VehicleType) {
    val label = getVehicleTypeLabel(type)

    val containerColor = when (type) {
        VehicleType.TAXI -> SoftYellow
        VehicleType.PRIVATE -> SoftGreen
    }

    val contentColor = when (type) {
        VehicleType.TAXI -> WarningOrange
        VehicleType.PRIVATE -> PrivateGreen
    }

    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun VehicleStatusChip(status: String) {
    Surface(
        color = SoftGreen,
        contentColor = PrivateGreen,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
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
            color = TaxiBlue,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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

fun formatCurrency(value: Long?): String {
    val amount = value ?: 0L
    return "$" + "%,d".format(Locale.US, amount).replace(",", ".")
}

fun VehicleDocumentType.shortLabel(): String {
    return when (this) {
        VehicleDocumentType.SOAT -> "S"
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> "T"
        VehicleDocumentType.TAXES -> "I"
    }
}
