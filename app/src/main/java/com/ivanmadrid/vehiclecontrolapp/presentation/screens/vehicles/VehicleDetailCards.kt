package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilLabel

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
    DetailSectionCard(
        title = "Información general",
        markerText = "i",
        markerColor = DetailBlue,
        markerBackground = SoftBlue
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DetailInfoTile(label = "Tipo", value = getVehicleTypeLabel(vehicle.type))
                DetailInfoTile(label = "Marca", value = vehicle.brand)
                DetailInfoTile(label = "Placa", value = vehicle.plate)
            }

            Column(modifier = Modifier.weight(1f)) {
                DetailInfoTile(label = "Estado", value = vehicle.status)
                DetailInfoTile(label = "Modelo", value = vehicle.model)
                DetailInfoTile(label = "ID", value = vehicle.id.toString())
            }
        }
    }
}

@Composable
fun TaxiInfoCard(vehicle: Vehicle) {
    DetailSectionCard(
        title = "Información del taxi",
        markerText = "T",
        markerColor = DetailOrange,
        markerBackground = SoftYellow
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HighlightInfoItem(
                label = "Conductor actual",
                value = vehicle.currentDriver ?: "Sin asignar",
                modifier = Modifier.weight(1f)
            )
            HighlightInfoItem(
                label = "Ingreso diario",
                value = formatCurrency(vehicle.dailyFixedIncome),
                valueColor = DetailGreen,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TaxiBalanceSummaryCard(
    vehicle: Vehicle,
    expenses: List<Expense>
) {
    val expectedIncome = vehicle.dailyFixedIncome ?: 0.0
    val totalExpenses = expenses.sumOf { expense ->
        expense.amount
    }
    val estimatedBalance = expectedIncome - totalExpenses

    DetailSectionCard(
        title = "Resumen económico",
        markerText = "$",
        markerColor = DetailGreen,
        markerBackground = SoftGreen
    ) {
        Text(
            text = "Estimación con los gastos recientes mostrados para este vehículo.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BalanceMetricItem(
                label = "Ingreso esperado",
                value = formatCurrency(expectedIncome),
                valueColor = DetailGreen,
                modifier = Modifier.weight(1f)
            )

            BalanceMetricItem(
                label = "Gastos",
                value = formatCurrency(totalExpenses),
                valueColor = DetailRed,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        BalanceMetricItem(
            label = "Balance estimado",
            value = formatCurrency(estimatedBalance),
            valueColor = if (estimatedBalance >= 0) DetailGreen else DetailRed,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun VehicleDocumentsCard(documents: List<VehicleDocument>) {
    DetailSectionCard(
        title = "Documentos y vencimientos",
        markerText = "D",
        markerColor = DetailBlue,
        markerBackground = SoftBlue,
        actionText = "Ver todos"
    ) {
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
        subtitle = getDaysUntilLabel(document.dueDate),
        extra = document.notes
    )
}

@Composable
fun VehicleExpensesCard(expenses: List<Expense>) {
    DetailSectionCard(
        title = "Gastos recientes",
        markerText = "$",
        markerColor = DetailBlue,
        markerBackground = SoftBlue,
        actionText = "Ver todos"
    ) {
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
        subtitle = getExpenseCategoryLabel(expense.category),
        extra = expense.date,
        trailingText = formatCurrency(expense.amount)
    )
}

@Composable
fun VehicleNoveltiesCard(novelties: List<Novelty>) {
    DetailSectionCard(
        title = "Novedades recientes",
        markerText = "N",
        markerColor = Color(0xFF6F35D4),
        markerBackground = Color(0xFFF0E7FF),
        actionText = "Ver todas"
    ) {
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
        extra = "${novelty.date} · Prioridad: ${getNoveltyPriorityLabel(novelty.priority)}"
    )
}

@Composable
fun VehicleQuickActionsCard(
    onRegisterExpenseClick: () -> Unit,
    onRegisterNoveltyClick: () -> Unit,
    onRegisterDocumentClick: () -> Unit
) {
    DetailSectionCard(
        title = "Acciones rápidas",
        markerText = "+",
        markerColor = DetailGreen,
        markerBackground = SoftGreen
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                label = "Registrar gasto",
                markerText = "$",
                markerColor = DetailBlue,
                backgroundColor = SoftBlue,
                modifier = Modifier.weight(1f),
                onClick = onRegisterExpenseClick
            )
            QuickActionTile(
                label = "Registrar novedad",
                markerText = "!",
                markerColor = Color(0xFF6F35D4),
                backgroundColor = Color(0xFFF0E7FF),
                modifier = Modifier.weight(1f),
                onClick = onRegisterNoveltyClick
            )
            QuickActionTile(
                label = "Registrar documento",
                markerText = "D",
                markerColor = DetailGreen,
                backgroundColor = SoftGreen,
                modifier = Modifier.weight(1f),
                onClick = onRegisterDocumentClick
            )
        }
    }
}

@Composable
fun DetailSectionCard(
    title: String,
    markerText: String,
    markerColor: Color,
    markerBackground: Color,
    actionText: String? = null,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailMarker(
                    text = markerText,
                    color = markerColor,
                    backgroundColor = markerBackground,
                    size = 34
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (actionText != null) {
                    Text(
                        text = actionText,
                        style = MaterialTheme.typography.labelMedium,
                        color = DetailBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun DetailInfoTile(
    label: String,
    value: String
) {
    Column {
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

    HorizontalDivider(
        modifier = Modifier.padding(top = 8.dp),
        color = DividerColor
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HighlightInfoItem(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
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
            color = valueColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BalanceMetricItem(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailListItem(
    markerText: String,
    markerColor: Color,
    markerBackground: Color,
    title: String,
    subtitle: String,
    extra: String?,
    trailingText: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        DetailMarker(
            text = markerText,
            color = markerColor,
            backgroundColor = markerBackground,
            size = 46
        )

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

        if (trailingText != null) {
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = trailingText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QuickActionTile(
    label: String,
    markerText: String,
    markerColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetailMarker(
                text = markerText,
                color = markerColor,
                backgroundColor = Color.White,
                size = 38
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = markerColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailMarker(
    text: String,
    color: Color,
    backgroundColor: Color,
    size: Int
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
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
