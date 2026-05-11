package com.ivanmadrid.vehiclecontrolapp.presentation.screens.reports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getVehicleDocumentIcon
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.formatCurrency
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.getDocumentTypeLabel
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.DocumentUrgency
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilCount
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilLabel
import com.ivanmadrid.vehiclecontrolapp.utils.getDocumentUrgency
import com.ivanmadrid.vehiclecontrolapp.utils.sortDocumentsByDueDate

@Composable
fun ReportsScreen(
    vehicles: List<Vehicle>,
    documents: List<VehicleDocument>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val taxiCount = vehicles.count { vehicle ->
        vehicle.type == VehicleType.TAXI
    }
    val privateCount = vehicles.count { vehicle ->
        vehicle.type == VehicleType.PRIVATE
    }
    val dailyTaxiIncome = vehicles
        .filter { vehicle -> vehicle.type == VehicleType.TAXI }
        .sumOf { vehicle -> vehicle.dailyFixedIncome ?: 0L }
    val urgentDocuments = documents.count { document ->
        val daysUntil = getDaysUntilCount(document.dueDate)
        daysUntil != null && daysUntil <= 15
    }
    val nextDocuments = sortDocumentsByDueDate(documents).take(3)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        AppBackButton(onClick = onBackClick)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Reportes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Resumen general de la operación.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ReportMetricCard(
                title = "Vehículos",
                value = vehicles.size.toString(),
                detail = "$taxiCount taxis · $privateCount particulares",
                markerIconRes = R.drawable.ic_vehicle_car,
                markerColor = MaterialTheme.vehicleColors.blue,
                markerBackground = MaterialTheme.vehicleColors.softBlue,
                modifier = Modifier.weight(1f)
            )

            ReportMetricCard(
                title = "Ingreso diario taxis",
                value = formatCurrency(dailyTaxiIncome),
                detail = "Suma de ingresos fijos",
                markerIconRes = R.drawable.ic_detail_taxi,
                markerColor = MaterialTheme.vehicleColors.orange,
                markerBackground = MaterialTheme.vehicleColors.softYellow,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ReportMetricCard(
            title = "Vencimientos por atender",
            value = urgentDocuments.toString(),
            detail = "Documentos vencidos o con 15 días o menos",
            markerIconRes = R.drawable.ic_detail_document,
            markerColor = if (urgentDocuments > 0) {
                MaterialTheme.vehicleColors.red
            } else {
                MaterialTheme.vehicleColors.green
            },
            markerBackground = if (urgentDocuments > 0) {
                MaterialTheme.vehicleColors.softRed
            } else {
                MaterialTheme.vehicleColors.softGreen
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Próximos vencimientos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (nextDocuments.isEmpty()) {
            ReportEmptyStateCard(
                title = "Sin vencimientos registrados",
                detail = "Cuando agregues documentos, aquí verás los próximos por atender."
            )
        } else {
            nextDocuments.forEach { document ->
                val urgency = getDocumentUrgency(getDaysUntilCount(document.dueDate))
                val vehiclePlate = vehicles.firstOrNull { vehicle ->
                    vehicle.id == document.vehicleId
                }?.plate ?: "Vehículo"

                ReportListCard(
                    title = vehiclePlate,
                    detail = "${getDocumentTypeLabel(document.type)} · ${getDaysUntilLabel(document.dueDate)} · ${document.dueDate}",
                    markerIconRes = getVehicleDocumentIcon(document.type),
                    urgency = urgency
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ReportMetricCard(
    title: String,
    value: String,
    detail: String,
    markerIconRes: Int,
    markerColor: Color,
    markerBackground: Color,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.vehicleColors

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            ReportMarker(
                iconRes = markerIconRes,
                color = markerColor,
                backgroundColor = markerBackground
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.blue,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ReportListCard(
    title: String,
    detail: String,
    markerIconRes: Int,
    urgency: DocumentUrgency = DocumentUrgency.UNKNOWN
) {
    val colors = MaterialTheme.vehicleColors
    val accentColor = when (urgency) {
        DocumentUrgency.OVERDUE,
        DocumentUrgency.URGENT -> colors.red
        DocumentUrgency.WARNING -> colors.orange
        DocumentUrgency.NORMAL,
        DocumentUrgency.UNKNOWN -> colors.green
    }
    val containerColor = when (urgency) {
        DocumentUrgency.OVERDUE,
        DocumentUrgency.URGENT -> colors.softRed
        DocumentUrgency.WARNING -> colors.softYellow
        DocumentUrgency.NORMAL,
        DocumentUrgency.UNKNOWN -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = accentColor.copy(alpha = 0.25f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReportMarker(
                iconRes = markerIconRes,
                color = accentColor,
                backgroundColor = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ReportEmptyStateCard(
    title: String,
    detail: String
) {
    val colors = MaterialTheme.vehicleColors

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colors.blue.copy(alpha = 0.18f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = colors.softBlue
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReportMarker(
                iconRes = R.drawable.ic_detail_document,
                color = colors.blue,
                backgroundColor = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ReportMarker(
    iconRes: Int,
    color: Color,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(color)
        )
    }
}
