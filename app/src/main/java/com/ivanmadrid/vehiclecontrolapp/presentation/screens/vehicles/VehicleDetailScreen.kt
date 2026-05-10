package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.utils.sortDocumentsByDueDate

@Composable
fun VehicleDetailScreen(
    vehicle: Vehicle,
    documents: List<VehicleDocument>,
    expenses: List<Expense>,
    novelties: List<Novelty>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditVehicleClick: () -> Unit,
    onDeleteVehicleClick: () -> Unit,
    onRegisterExpenseClick: () -> Unit,
    onRegisterNoveltyClick: () -> Unit,
    onRegisterDocumentClick: () -> Unit
) {
    val vehicleDocuments = sortDocumentsByDueDate(documents)
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onBackClick
            ) {
                Text(text = "< Volver")
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onEditVehicleClick
            ) {
                Text(text = "Editar")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        VehicleDetailHeader(vehicle = vehicle)

        Spacer(modifier = Modifier.height(22.dp))

        VehicleGeneralInfoCard(vehicle = vehicle)

        if (vehicle.type == VehicleType.TAXI) {
            Spacer(modifier = Modifier.height(12.dp))
            TaxiInfoCard(vehicle = vehicle)
        }

        Spacer(modifier = Modifier.height(12.dp))

        VehicleDocumentsCard(documents = vehicleDocuments)

        Spacer(modifier = Modifier.height(12.dp))

        VehicleExpensesCard(expenses = expenses)

        if (vehicle.type == VehicleType.TAXI) {
            Spacer(modifier = Modifier.height(12.dp))
            TaxiBalanceSummaryCard(
                vehicle = vehicle,
                expenses = expenses,
                novelties = novelties
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        VehicleNoveltiesCard(novelties = novelties)

        Spacer(modifier = Modifier.height(12.dp))

        VehicleQuickActionsCard(
            onRegisterExpenseClick = onRegisterExpenseClick,
            onRegisterNoveltyClick = onRegisterNoveltyClick,
            onRegisterDocumentClick = onRegisterDocumentClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                showDeleteDialog = true
            }
        ) {
            Text(text = "Eliminar vehículo")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(text = "Eliminar vehículo")
            },
            text = {
                Text(
                    text = "¿Seguro que quieres eliminar ${vehicle.plate}? También se eliminarán sus gastos, novedades y documentos."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteVehicleClick()
                    }
                ) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
fun VehicleDetailHeader(vehicle: Vehicle) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = vehicle.plate,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${vehicle.brand} ${vehicle.model}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                VehicleTypeChip(type = vehicle.type)

                Spacer(modifier = Modifier.width(8.dp))

                VehicleStatusChip(status = vehicle.status)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        VehicleAvatar(type = vehicle.type)
    }
}
