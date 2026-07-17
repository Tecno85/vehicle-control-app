package com.ivanmadrid.vehiclecontrolapp.presentation.screens.reports

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.formatCurrency
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.getDocumentTypeLabel
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.DocumentUrgency
import com.ivanmadrid.vehiclecontrolapp.utils.formatIsoDateForDisplay
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
                accentColor = MaterialTheme.vehicleColors.blue,
                modifier = Modifier.weight(1f)
            )

            ReportMetricCard(
                title = "Ingreso diario taxis",
                value = formatCurrency(dailyTaxiIncome),
                detail = "Suma de ingresos fijos",
                accentColor = MaterialTheme.vehicleColors.orange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ReportMetricCard(
            title = "Vencimientos por atender",
            value = urgentDocuments.toString(),
            detail = "Documentos vencidos o con 15 días o menos",
            accentColor = if (urgentDocuments > 0) {
                MaterialTheme.vehicleColors.red
            } else {
                MaterialTheme.vehicleColors.green
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
                    detail = "${getDocumentTypeLabel(document.type)} · ${getDaysUntilLabel(document.dueDate)} · ${formatIsoDateForDisplay(document.dueDate)}",
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
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(34.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(accentColor)
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                color = accentColor,
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

@Composable
fun ReportListCard(
    title: String,
    detail: String,
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = accentColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = detail,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ReportEmptyStateCard(
    title: String,
    detail: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
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
