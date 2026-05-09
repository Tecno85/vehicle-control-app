package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument

private val DetailBlue = Color(0xFF0B63CE)
private val DetailGreen = Color(0xFF188038)
private val DetailOrange = Color(0xFFE8710A)
private val DetailRed = Color(0xFFD93025)
private val SoftBlue = Color(0xFFE8F1FF)
private val SoftGreen = Color(0xFFE8F5E9)
private val SoftYellow = Color(0xFFFFF7DB)
private val SoftRed = Color(0xFFFFECEC)
private val DividerColor = Color(0xFFE4E7EC)

@Composable
fun VehicleGeneralInfoCard(vehicle: Vehicle) {
    DetailSectionCard(title = "Información general") {
        DetailInfoRow(label = "Tipo", value = getVehicleTypeLabel(vehicle.type))
        DetailInfoRow(label = "Estado", value = vehicle.status)
        DetailInfoRow(label = "Marca", value = vehicle.brand)
        DetailInfoRow(label = "Modelo", value = vehicle.model)
    }
}

@Composable
fun TaxiInfoCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, DetailBlue.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información del taxi",
                style = MaterialTheme.typography.titleMedium,
                color = DetailBlue,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HighlightInfoItem(
                    label = "Conductor",
                    value = vehicle.currentDriver ?: "Sin asignar",
                    modifier = Modifier.weight(1f)
                )
                HighlightInfoItem(
                    label = "Ingreso diario",
                    value = formatCurrency(vehicle.dailyFixedIncome),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun VehicleDocumentsCard(documents: List<VehicleDocument>) {
    DetailSectionCard(title = "Documentos y vencimientos") {
        if (documents.isEmpty()) {
            EmptySectionText(text = "No hay documentos registrados para este vehículo.")
        } else {
            documents.forEachIndexed { index, document ->
                VehicleDocumentItem(document = document)

                if (index < documents.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleDocumentItem(document: VehicleDocument) {
    DetailListItem(
        markerText = document.type.shortLabel(),
        markerColor = DetailOrange,
        markerBackground = SoftYellow,
        title = getDocumentTypeLabel(document.type),
        subtitle = "Vence el ${document.dueDate}",
        extra = document.notes
    )
}

@Composable
fun VehicleExpensesCard(expenses: List<Expense>) {
    DetailSectionCard(title = "Gastos recientes") {
        if (expenses.isEmpty()) {
            EmptySectionText(text = "No hay gastos registrados para este vehículo.")
        } else {
            expenses.forEachIndexed { index, expense ->
                VehicleExpenseItem(expense = expense)

                if (index < expenses.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleExpenseItem(expense: Expense) {
    DetailListItem(
        markerText = "$",
        markerColor = DetailBlue,
        markerBackground = SoftBlue,
        title = expense.description,
        subtitle = "${getExpenseCategoryLabel(expense.category)} - ${formatCurrency(expense.amount)}",
        extra = expense.date
    )
}

@Composable
fun VehicleNoveltiesCard(novelties: List<Novelty>) {
    DetailSectionCard(title = "Novedades recientes") {
        if (novelties.isEmpty()) {
            EmptySectionText(text = "No hay novedades registradas para este vehículo.")
        } else {
            novelties.forEachIndexed { index, novelty ->
                VehicleNoveltyItem(novelty = novelty)

                if (index < novelties.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleNoveltyItem(novelty: Novelty) {
    val markerColor = when (novelty.priority) {
        NoveltyPriority.LOW -> DetailGreen
        NoveltyPriority.MEDIUM -> DetailOrange
        NoveltyPriority.HIGH -> DetailRed
    }

    val markerBackground = when (novelty.priority) {
        NoveltyPriority.LOW -> SoftGreen
        NoveltyPriority.MEDIUM -> SoftYellow
        NoveltyPriority.HIGH -> SoftRed
    }

    DetailListItem(
        markerText = getNoveltyPriorityLabel(novelty.priority).take(1),
        markerColor = markerColor,
        markerBackground = markerBackground,
        title = novelty.type,
        subtitle = novelty.description,
        extra = "${getNoveltyPriorityLabel(novelty.priority)} · ${novelty.date}"
    )
}

@Composable
fun VehicleQuickActionsCard(
    onRegisterExpenseClick: () -> Unit
) {
    DetailSectionCard(title = "Acciones rápidas") {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRegisterExpenseClick
        ) {
            Text(text = "Registrar gasto")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO: Abrir formulario para registrar novedad
            }
        ) {
            Text(text = "Registrar novedad")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO: Abrir formulario para registrar documento
            }
        ) {
            Text(text = "Registrar documento")
        }
    }
}

@Composable
fun DetailSectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun DetailInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HighlightInfoItem(
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
            color = DetailBlue,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DetailListItem(
    markerText: String,
    markerColor: Color,
    markerBackground: Color,
    title: String,
    subtitle: String,
    extra: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(markerBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = markerText,
                style = MaterialTheme.typography.labelLarge,
                color = markerColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!extra.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = extra,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun EmptySectionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun DetailDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = DividerColor
    )
}

fun getExpenseCategoryLabel(category: ExpenseCategory): String {
    return when (category) {
        ExpenseCategory.FUEL -> "Combustible"
        ExpenseCategory.WASH -> "Lavado"
        ExpenseCategory.MAINTENANCE -> "Mantenimiento"
        ExpenseCategory.SPARE_PARTS -> "Repuestos"
        ExpenseCategory.INSURANCE -> "Seguro"
        ExpenseCategory.TAXES -> "Impuestos"
        ExpenseCategory.FINES -> "Multas"
        ExpenseCategory.OTHER -> "Otros"
    }
}

fun getNoveltyPriorityLabel(priority: NoveltyPriority): String {
    return when (priority) {
        NoveltyPriority.LOW -> "Baja"
        NoveltyPriority.MEDIUM -> "Media"
        NoveltyPriority.HIGH -> "Alta"
    }
}
