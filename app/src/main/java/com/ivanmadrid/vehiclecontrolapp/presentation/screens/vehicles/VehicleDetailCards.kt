package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getExpenseCategoryIcon
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getVehicleDocumentIcon
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.DocumentUrgency
import com.ivanmadrid.vehiclecontrolapp.utils.calculateTaxiBalanceSummary
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilCount
import com.ivanmadrid.vehiclecontrolapp.utils.getTaxiBalanceReferenceDates
import com.ivanmadrid.vehiclecontrolapp.utils.getDaysUntilLabel
import com.ivanmadrid.vehiclecontrolapp.utils.getDocumentUrgency

@Composable
fun VehicleGeneralInfoCard(vehicle: Vehicle) {
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Información general",
        markerText = "i",
        markerIconRes = R.drawable.ic_detail_info,
        markerColor = colors.blue,
        markerBackground = colors.softBlue
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Información del taxi",
        markerText = "T",
        markerIconRes = R.drawable.ic_detail_taxi,
        markerColor = colors.orange,
        markerBackground = colors.softYellow
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HighlightInfoItem(
                label = "Conductor actual",
                value = vehicle.currentDriver ?: "Sin asignar",
                modifier = Modifier.weight(1f)
            )
            HighlightInfoItem(
                label = "Ingreso diario",
                value = formatCurrency(vehicle.dailyFixedIncome),
                valueColor = colors.green,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TaxiBalanceSummaryCard(
    vehicle: Vehicle,
    expenses: List<Expense>,
    novelties: List<Novelty>
) {
    val colors = MaterialTheme.vehicleColors
    val referenceDates = getTaxiBalanceReferenceDates(
        expenses = expenses,
        novelties = novelties
    )
    var selectedReferenceDate by remember(referenceDates) {
        mutableStateOf(referenceDates.firstOrNull())
    }
    val balanceSummary = calculateTaxiBalanceSummary(
        vehicle = vehicle,
        expenses = expenses,
        novelties = novelties,
        referenceDate = selectedReferenceDate
    )

    DetailSectionCard(
        title = "Resumen económico",
        markerText = "$",
        markerIconRes = R.drawable.ic_detail_expense,
        markerColor = colors.green,
        markerBackground = colors.softGreen
    ) {
        Text(
            text = if (balanceSummary.referenceDate != null) {
                "Día seleccionado: ${balanceSummary.referenceDate}. Solo usa gastos y novedades de ese día."
            } else {
                "Sin gastos ni novedades con fecha válida para calcular un día de referencia."
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (referenceDates.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                referenceDates.take(3).forEach { date ->
                    BalanceDateButton(
                        date = date,
                        selected = selectedReferenceDate == date,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedReferenceDate = date
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BalanceMetricItem(
                label = "Ingreso esperado",
                value = formatCurrency(balanceSummary.baseIncome),
                valueColor = colors.green,
                modifier = Modifier.weight(1f)
            )

            BalanceMetricItem(
                label = "Ingreso ajustado",
                value = formatCurrency(balanceSummary.adjustedIncome),
                valueColor = colors.green,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BalanceMetricItem(
            label = "Gastos",
            value = formatCurrency(balanceSummary.totalExpenses),
            valueColor = colors.red,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        BalanceMetricItem(
            label = "Balance estimado",
            value = formatCurrency(balanceSummary.estimatedBalance),
            valueColor = if (balanceSummary.estimatedBalance >= 0) colors.green else colors.red,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BalanceDateButton(
    date: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val contentColor = if (selected) {
        colors.green
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val containerColor = if (selected) {
        colors.softGreen
    } else {
        MaterialTheme.colorScheme.surface
    }
    val borderColor = if (selected) {
        colors.green.copy(alpha = 0.45f)
    } else {
        colors.divider
    }

    Card(
        modifier = modifier
            .height(34.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getShortDateLabel(date),
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun getShortDateLabel(date: String): String {
    val parts = date.split("-")

    if (parts.size != 3) {
        return date
    }

    return "${parts[1]}/${parts[2]}"
}

@Composable
fun VehicleDocumentsCard(
    documents: List<VehicleDocument>,
    onEditDocumentClick: (VehicleDocument) -> Unit,
    onDeleteDocumentClick: (VehicleDocument) -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Documentos y vencimientos",
        markerText = "D",
        markerIconRes = R.drawable.ic_detail_document,
        markerColor = colors.blue,
        markerBackground = colors.softBlue
    ) {
        if (documents.isEmpty()) {
            EmptySectionMessage(
                title = "Sin documentos registrados",
                description = "Cuando registres SOAT, tecnicomecánica o impuestos, aparecerán aquí.",
                markerText = "D",
                markerIconRes = R.drawable.ic_detail_document,
                markerColor = colors.blue,
                markerBackground = colors.softBlue
            )
        } else {
            documents.forEachIndexed { index, document ->
                VehicleDocumentItem(
                    document = document,
                    onEditClick = {
                        onEditDocumentClick(document)
                    },
                    onDeleteClick = {
                        onDeleteDocumentClick(document)
                    }
                )

                if (index < documents.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleDocumentItem(
    document: VehicleDocument,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val urgency = getDocumentUrgency(getDaysUntilCount(document.dueDate))
    val markerColor = when (urgency) {
        DocumentUrgency.OVERDUE,
        DocumentUrgency.URGENT -> colors.red
        DocumentUrgency.WARNING -> colors.orange
        DocumentUrgency.NORMAL,
        DocumentUrgency.UNKNOWN -> colors.green
    }
    val markerBackground = when (urgency) {
        DocumentUrgency.OVERDUE,
        DocumentUrgency.URGENT -> colors.softRed
        DocumentUrgency.WARNING -> colors.softYellow
        DocumentUrgency.NORMAL,
        DocumentUrgency.UNKNOWN -> colors.softGreen
    }

    DetailListItem(
        markerText = document.type.shortLabel(),
        markerIconRes = getVehicleDocumentIcon(document.type),
        markerColor = markerColor,
        markerBackground = markerBackground,
        title = getDocumentTypeLabel(document.type),
        subtitle = getDaysUntilLabel(document.dueDate),
        extra = document.notes,
        actionText = "Editar",
        onActionClick = onEditClick,
        secondaryActionText = "Eliminar",
        onSecondaryActionClick = onDeleteClick
    )
}

@Composable
fun VehicleExpensesCard(
    expenses: List<Expense>,
    onEditExpenseClick: (Expense) -> Unit,
    onDeleteExpenseClick: (Expense) -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Gastos recientes",
        markerText = "$",
        markerIconRes = R.drawable.ic_detail_expense,
        markerColor = colors.blue,
        markerBackground = colors.softBlue
    ) {
        if (expenses.isEmpty()) {
            EmptySectionMessage(
                title = "Sin gastos recientes",
                description = "Los gastos registrados para este vehículo se mostrarán en esta sección.",
                markerText = "$",
                markerIconRes = R.drawable.ic_detail_expense,
                markerColor = colors.blue,
                markerBackground = colors.softBlue
            )
        } else {
            expenses.forEachIndexed { index, expense ->
                VehicleExpenseItem(
                    expense = expense,
                    onEditClick = {
                        onEditExpenseClick(expense)
                    },
                    onDeleteClick = {
                        onDeleteExpenseClick(expense)
                    }
                )

                if (index < expenses.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleExpenseItem(
    expense: Expense,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    DetailListItem(
        markerText = "$",
        markerIconRes = getExpenseCategoryIcon(expense.category),
        markerColor = colors.blue,
        markerBackground = colors.softBlue,
        title = expense.description,
        subtitle = getExpenseCategoryLabel(expense.category),
        extra = expense.date,
        trailingText = formatCurrency(expense.amount),
        actionText = "Editar",
        onActionClick = onEditClick,
        secondaryActionText = "Eliminar",
        onSecondaryActionClick = onDeleteClick
    )
}

@Composable
fun VehicleNoveltiesCard(
    novelties: List<Novelty>,
    onEditNoveltyClick: (Novelty) -> Unit,
    onDeleteNoveltyClick: (Novelty) -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Novedades recientes",
        markerText = "N",
        markerIconRes = R.drawable.ic_detail_novelty,
        markerColor = colors.purple,
        markerBackground = colors.softPurple
    ) {
        if (novelties.isEmpty()) {
            EmptySectionMessage(
                title = "Sin novedades recientes",
                description = "Las novedades operativas o económicas del vehículo aparecerán aquí.",
                markerText = "N",
                markerIconRes = R.drawable.ic_detail_novelty,
                markerColor = colors.purple,
                markerBackground = colors.softPurple
            )
        } else {
            novelties.forEachIndexed { index, novelty ->
                VehicleNoveltyItem(
                    novelty = novelty,
                    onEditClick = {
                        onEditNoveltyClick(novelty)
                    },
                    onDeleteClick = {
                        onDeleteNoveltyClick(novelty)
                    }
                )

                if (index < novelties.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
fun VehicleNoveltyItem(
    novelty: Novelty,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val markerColor = when (novelty.priority) {
        NoveltyPriority.LOW -> colors.green
        NoveltyPriority.MEDIUM -> colors.orange
        NoveltyPriority.HIGH -> colors.red
    }

    val markerBackground = when (novelty.priority) {
        NoveltyPriority.LOW -> colors.softGreen
        NoveltyPriority.MEDIUM -> colors.softYellow
        NoveltyPriority.HIGH -> colors.softRed
    }

    DetailListItem(
        markerText = getNoveltyPriorityLabel(novelty.priority).take(1),
        markerIconRes = R.drawable.ic_detail_novelty,
        markerColor = markerColor,
        markerBackground = markerBackground,
        title = novelty.type,
        subtitle = novelty.description,
        extra = getNoveltyExtraLabel(novelty),
        actionText = "Editar",
        onActionClick = onEditClick,
        secondaryActionText = "Eliminar",
        onSecondaryActionClick = onDeleteClick
    )
}

fun getNoveltyExtraLabel(novelty: Novelty): String {
    val baseLabel = "${novelty.date} · Prioridad: ${getNoveltyPriorityLabel(novelty.priority)}"

    if (!novelty.affectsIncome) {
        return baseLabel
    }

    val incomeAdjustmentLabel = when (novelty.incomeAdjustmentType) {
        IncomeAdjustmentType.NO_INCOME -> "Sin ingreso"
        IncomeAdjustmentType.HALF_INCOME -> "Medio ingreso"
        IncomeAdjustmentType.CUSTOM_AMOUNT -> {
            "Ingreso diferente ${formatCurrency(novelty.adjustedIncomeAmount)}"
        }
        null -> "Por revisar"
    }

    return "$baseLabel · Operación: $incomeAdjustmentLabel"
}

@Composable
fun VehicleQuickActionsCard(
    onRegisterExpenseClick: () -> Unit,
    onRegisterNoveltyClick: () -> Unit,
    onRegisterDocumentClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    DetailSectionCard(
        title = "Acciones rápidas",
        markerText = "+",
        markerIconRes = R.drawable.ic_detail_action,
        markerColor = colors.green,
        markerBackground = colors.softGreen
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickActionTile(
                label = "Registrar gasto",
                markerText = "$",
                markerIconRes = R.drawable.ic_detail_expense,
                markerColor = colors.blue,
                backgroundColor = colors.softBlue,
                modifier = Modifier.weight(1f),
                onClick = onRegisterExpenseClick
            )
            QuickActionTile(
                label = "Registrar novedad",
                markerText = "!",
                markerIconRes = R.drawable.ic_detail_novelty,
                markerColor = colors.purple,
                backgroundColor = colors.softPurple,
                modifier = Modifier.weight(1f),
                onClick = onRegisterNoveltyClick
            )
            QuickActionTile(
                label = "Registrar documento",
                markerText = "D",
                markerIconRes = R.drawable.ic_detail_document,
                markerColor = colors.green,
                backgroundColor = colors.softGreen,
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
    markerIconRes: Int? = null,
    markerColor: Color,
    markerBackground: Color,
    actionText: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailMarker(
                    text = markerText,
                    iconRes = markerIconRes,
                    color = markerColor,
                    backgroundColor = markerBackground,
                    size = 30
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                if (actionText != null) {
                    Text(
                        text = actionText,
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            content()
        }
    }
}

@Composable
fun DetailInfoTile(
    label: String,
    value: String
) {
    val colors = MaterialTheme.vehicleColors
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }

    HorizontalDivider(
        modifier = Modifier.padding(top = 6.dp),
        color = colors.divider
    )

    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun HighlightInfoItem(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
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
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailListItem(
    markerText: String,
    markerIconRes: Int? = null,
    markerColor: Color,
    markerBackground: Color,
    title: String,
    subtitle: String,
    extra: String?,
    trailingText: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    secondaryActionText: String? = null,
    onSecondaryActionClick: (() -> Unit)? = null
) {
    val hasActions = (actionText != null && onActionClick != null) ||
        (secondaryActionText != null && onSecondaryActionClick != null)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        DetailMarker(
            text = markerText,
            iconRes = markerIconRes,
            color = markerColor,
            backgroundColor = markerBackground,
            size = 40
        )

        Spacer(modifier = Modifier.width(10.dp))

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
                style = MaterialTheme.typography.bodySmall,
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

        if (trailingText != null || hasActions) {
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (trailingText != null) {
                    Text(
                        text = trailingText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (actionText != null && onActionClick != null) {
                        TextButton(
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
                            onClick = onActionClick
                        ) {
                            Text(
                                text = actionText,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }

                    if (secondaryActionText != null && onSecondaryActionClick != null) {
                        InlineDeleteButton(
                            text = secondaryActionText,
                            onClick = onSecondaryActionClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InlineDeleteButton(
    text: String,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(colors.softRed.copy(alpha = 0.72f))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier.size(13.dp),
            colorFilter = ColorFilter.tint(colors.red)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = colors.red,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun QuickActionTile(
    label: String,
    markerText: String,
    markerIconRes: Int? = null,
    markerColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    Card(
        modifier = modifier
            .heightIn(min = 92.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetailMarker(
                text = markerText,
                iconRes = markerIconRes,
                color = markerColor,
                backgroundColor = colors.avatarSurface,
                size = 34
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = markerColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
fun DetailMarker(
    text: String,
    iconRes: Int? = null,
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
        if (iconRes == null) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
        } else {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size((size * 0.58f).dp),
                colorFilter = ColorFilter.tint(color)
            )
        }
    }
}

@Composable
fun EmptySectionMessage(
    title: String,
    description: String,
    markerText: String,
    markerIconRes: Int,
    markerColor: Color,
    markerBackground: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(markerBackground.copy(alpha = 0.55f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DetailMarker(
            text = markerText,
            iconRes = markerIconRes,
            color = markerColor,
            backgroundColor = MaterialTheme.colorScheme.surface,
            size = 38
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DetailDivider() {
    val colors = MaterialTheme.vehicleColors
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = colors.divider
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
