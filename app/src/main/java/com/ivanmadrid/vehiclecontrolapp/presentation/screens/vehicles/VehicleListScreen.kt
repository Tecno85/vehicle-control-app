package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilLabel
import com.ivanmadrid.vehiclecontrolapp.utils.sortDocumentsByDueDate
import java.util.Locale

@Composable
fun VehicleListScreen(
    vehicles: List<Vehicle>,
    documents: List<VehicleDocument>,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onToggleThemeClick: () -> Unit,
    onReportsClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit
) {
    var showMainMenu by remember {
        mutableStateOf(false)
    }
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
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderIconButton(
                iconRes = R.drawable.ic_menu,
                contentDescription = "Abrir menú principal",
                onClick = {
                    showMainMenu = true
                }
            )

            DropdownMenu(
                expanded = showMainMenu,
                onDismissRequest = {
                    showMainMenu = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = "Vehículos")
                    },
                    onClick = {
                        showMainMenu = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Reportes")
                    },
                    onClick = {
                        showMainMenu = false
                        onReportsClick()
                    }
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Control Vehicular",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            ThemeToggleButton(
                isDarkTheme = isDarkTheme,
                onClick = onToggleThemeClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            SummaryMetricChip(
                count = vehicles.size,
                label = "vehículos",
                modifier = Modifier.weight(1f)
            )
            SummaryMetricChip(
                count = taxiCount,
                label = "taxis",
                type = VehicleType.TAXI,
                modifier = Modifier.weight(1f)
            )
            SummaryMetricChip(
                count = privateCount,
                label = "particulares",
                type = VehicleType.PRIVATE,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        SectionTitle(title = "Próximos vencimientos")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sortDocumentsByDueDate(documents).take(2).forEachIndexed { index, document ->
                DocumentReminderCard(
                    document = document,
                    vehicles = vehicles,
                    isUrgent = index == 0,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

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
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SummaryMetricChip(
    count: Int,
    label: String,
    modifier: Modifier = Modifier,
    type: VehicleType? = null
) {
    val colors = MaterialTheme.vehicleColors
    val chipColor = when (type) {
        VehicleType.TAXI -> colors.blue
        VehicleType.PRIVATE -> colors.green
        null -> colors.blue
    }

    val chipBackground = when (type) {
        VehicleType.TAXI -> colors.softBlue
        VehicleType.PRIVATE -> colors.softGreen
        null -> MaterialTheme.colorScheme.surface
    }

    Surface(
        modifier = modifier,
        color = chipBackground,
        contentColor = chipColor,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = chipColor.copy(alpha = 0.18f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = chipColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DocumentReminderCard(
    document: VehicleDocument,
    vehicles: List<Vehicle>,
    isUrgent: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.vehicleColors
    val vehicle = vehicles.firstOrNull { vehicle ->
        vehicle.id == document.vehicleId
    }

    val vehiclePlate = vehicle?.plate ?: "Vehículo"
    val accentColor = if (isUrgent) colors.red else colors.orange
    val containerColor = if (isUrgent) colors.softRed else colors.softYellow

    Card(
        modifier = modifier.padding(top = 6.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
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

            Spacer(modifier = Modifier.width(8.dp))

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
    val colors = MaterialTheme.vehicleColors
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VehicleAvatar(
                type = vehicle.type,
                size = 68
            )

            Spacer(modifier = Modifier.width(14.dp))

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
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${vehicle.brand} ${vehicle.model}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    VehicleStatusChip(status = vehicle.status)
                }

                Spacer(modifier = Modifier.height(6.dp))

                VehicleTypeChip(type = vehicle.type)

                if (vehicle.type == VehicleType.TAXI) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = colors.divider
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

                        Spacer(modifier = Modifier.width(10.dp))

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
fun VehicleAvatar(
    type: VehicleType,
    size: Int = 84
) {
    val colors = MaterialTheme.vehicleColors
    val backgroundColor = when (type) {
        VehicleType.TAXI -> colors.softYellow
        VehicleType.PRIVATE -> colors.softBlue
    }

    val iconRes = when (type) {
        VehicleType.TAXI -> R.drawable.ic_vehicle_taxi
        VehicleType.PRIVATE -> R.drawable.ic_vehicle_car
    }

    val contentDescription = when (type) {
        VehicleType.TAXI -> "Taxi"
        VehicleType.PRIVATE -> "Vehículo particular"
    }

    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size((size * 0.76f).dp)
        )
    }
}

@Composable
fun VehicleTypeChip(type: VehicleType) {
    val colors = MaterialTheme.vehicleColors
    val label = getVehicleTypeLabel(type)

    val containerColor = when (type) {
        VehicleType.TAXI -> colors.softYellow
        VehicleType.PRIVATE -> colors.softGreen
    }

    val contentColor = when (type) {
        VehicleType.TAXI -> colors.orange
        VehicleType.PRIVATE -> colors.green
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
    val colors = MaterialTheme.vehicleColors
    Surface(
        color = colors.softGreen,
        contentColor = colors.green,
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
    val colors = MaterialTheme.vehicleColors
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colors.blue,
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

@Composable
fun ThemeToggleButton(
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    val iconRes = if (isDarkTheme) {
        R.drawable.ic_theme_sun
    } else {
        R.drawable.ic_theme_moon
    }

    HeaderIconButton(
        iconRes = iconRes,
        contentDescription = if (isDarkTheme) {
            "Cambiar a modo claro"
        } else {
            "Cambiar a modo oscuro"
        },
        onClick = onClick
    )
}

@Composable
fun HeaderIconButton(
    iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(42.dp)
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
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
