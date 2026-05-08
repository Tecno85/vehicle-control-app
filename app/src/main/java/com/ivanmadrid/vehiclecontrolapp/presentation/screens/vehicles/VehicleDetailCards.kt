package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument

@Composable
fun VehicleGeneralInfoCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información general",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Tipo: ${getVehicleTypeLabel(vehicle.type)}")
            Text(text = "Estado: ${vehicle.status}")
            Text(text = "Marca: ${vehicle.brand}")
            Text(text = "Modelo: ${vehicle.model}")
        }
    }
}

@Composable
fun TaxiInfoCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Información del taxi",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Conductor: ${vehicle.currentDriver ?: "Sin asignar"}",
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = "Ingreso diario: ${formatCurrency(vehicle.dailyFixedIncome)}",
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun VehicleDocumentsCard(documents: List<VehicleDocument>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Documentos y vencimientos",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (documents.isEmpty()) {
                Text(
                    text = "No hay documentos registrados para este vehículo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                documents.forEach { document ->
                    VehicleDocumentItem(document = document)
                }
            }
        }
    }
}

@Composable
fun VehicleDocumentItem(document: VehicleDocument) {
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = getDocumentTypeLabel(document.type),
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = "Vence el ${document.dueDate}",
            style = MaterialTheme.typography.bodyMedium
        )

        if (!document.notes.isNullOrBlank()) {
            Text(
                text = document.notes,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun VehicleExpensesCard(expenses: List<Expense>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Gastos recientes",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (expenses.isEmpty()) {
                Text(
                    text = "No hay gastos registrados para este vehículo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                expenses.forEach { expense ->
                    VehicleExpenseItem(expense = expense)
                }
            }
        }
    }
}

@Composable
fun VehicleExpenseItem(expense: Expense) {
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = expense.description,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = "${getExpenseCategoryLabel(expense.category)} - ${formatCurrency(expense.amount)}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = expense.date,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun VehicleNoveltiesCard(novelties: List<Novelty>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Novedades recientes",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (novelties.isEmpty()) {
                Text(
                    text = "No hay novedades registradas para este vehículo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                novelties.forEach { novelty ->
                    VehicleNoveltyItem(novelty = novelty)
                }
            }
        }
    }
}

@Composable
fun VehicleNoveltyItem(novelty: Novelty) {
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = novelty.type,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = novelty.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Prioridad: ${getNoveltyPriorityLabel(novelty.priority)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = novelty.date,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun VehicleQuickActionsCard(
    onRegisterExpenseClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Acciones rápidas",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
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