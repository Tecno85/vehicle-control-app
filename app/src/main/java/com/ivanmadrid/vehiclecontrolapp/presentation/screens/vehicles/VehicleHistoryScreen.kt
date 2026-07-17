package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getExpenseCategoryIcon
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getVehicleDocumentIcon
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.formatIsoDateForDisplay

@Composable
fun VehicleHistoryScreen(
    vehicle: Vehicle,
    documents: List<VehicleDocument>,
    expenses: List<Expense>,
    novelties: List<Novelty>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val historyItems = buildVehicleHistoryItems(
        documents = documents,
        expenses = expenses,
        novelties = novelties
    )
    val groupedItems = historyItems.groupBy { it.date }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        AppBackButton(onClick = onBackClick)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Historial del vehículo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${vehicle.plate} · ${vehicle.brand} ${vehicle.model}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (historyItems.isEmpty()) {
            EmptySectionMessage(
                title = "Sin historial registrado",
                description = "Cuando registres gastos, novedades o documentos, aparecerán aquí."
            )
        } else {
            groupedItems.forEach { (date, items) ->
                HistoryDateGroup(
                    date = date,
                    items = items
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun HistoryDateGroup(
    date: String,
    items: List<VehicleHistoryItem>
) {
    DetailSectionCard(
        title = formatIsoDateForDisplay(date)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEachIndexed { index, item ->
                HistoryItemRow(item = item)

                if (index < items.lastIndex) {
                    DetailDivider()
                }
            }
        }
    }
}

@Composable
private fun HistoryItemRow(
    item: VehicleHistoryItem
) {
    DetailListItem(
        markerText = item.markerText,
        markerIconRes = item.markerIconRes,
        markerColor = item.markerColor(),
        markerBackground = item.markerBackground(),
        title = item.title,
        subtitle = item.subtitle,
        extra = item.extra,
        trailingText = item.trailingText
    )
}

private data class VehicleHistoryItem(
    val date: String,
    val markerText: String,
    val markerIconRes: Int,
    val title: String,
    val subtitle: String,
    val extra: String?,
    val trailingText: String?,
    val type: VehicleHistoryItemType
)

private enum class VehicleHistoryItemType {
    EXPENSE,
    NOVELTY,
    DOCUMENT
}

@Composable
private fun VehicleHistoryItem.markerColor(): Color {
    val colors = MaterialTheme.vehicleColors
    return when (type) {
        VehicleHistoryItemType.EXPENSE -> colors.blue
        VehicleHistoryItemType.NOVELTY -> colors.purple
        VehicleHistoryItemType.DOCUMENT -> colors.green
    }
}

@Composable
private fun VehicleHistoryItem.markerBackground(): Color {
    val colors = MaterialTheme.vehicleColors
    return when (type) {
        VehicleHistoryItemType.EXPENSE -> colors.softBlue
        VehicleHistoryItemType.NOVELTY -> colors.softPurple
        VehicleHistoryItemType.DOCUMENT -> colors.softGreen
    }
}

private fun buildVehicleHistoryItems(
    documents: List<VehicleDocument>,
    expenses: List<Expense>,
    novelties: List<Novelty>
): List<VehicleHistoryItem> {
    val expenseItems = expenses.map { expense ->
        VehicleHistoryItem(
            date = expense.date,
            markerText = "$",
            markerIconRes = getExpenseCategoryIcon(expense.category),
            title = expense.description.ifBlank { getExpenseCategoryLabel(expense.category) },
            subtitle = "Gasto · ${getExpenseCategoryLabel(expense.category)}",
            extra = null,
            trailingText = formatCurrency(expense.amount),
            type = VehicleHistoryItemType.EXPENSE
        )
    }

    val noveltyItems = novelties.map { novelty ->
        VehicleHistoryItem(
            date = novelty.date,
            markerText = "!",
            markerIconRes = R.drawable.ic_detail_novelty,
            title = novelty.type,
            subtitle = "Novedad · Prioridad ${getNoveltyPriorityLabel(novelty.priority)}",
            extra = novelty.description,
            trailingText = null,
            type = VehicleHistoryItemType.NOVELTY
        )
    }

    val documentItems = documents.map { document ->
        VehicleHistoryItem(
            date = document.dueDate,
            markerText = "D",
            markerIconRes = getVehicleDocumentIcon(document.type),
            title = getDocumentTypeLabel(document.type),
            subtitle = "Documento · vence ${formatIsoDateForDisplay(document.dueDate)}",
            extra = document.notes,
            trailingText = null,
            type = VehicleHistoryItemType.DOCUMENT
        )
    }

    return (expenseItems + noveltyItems + documentItems)
        .sortedWith(
            compareByDescending<VehicleHistoryItem> { it.date }
                .thenBy { it.type.ordinal }
                .thenBy { it.title }
        )
}
