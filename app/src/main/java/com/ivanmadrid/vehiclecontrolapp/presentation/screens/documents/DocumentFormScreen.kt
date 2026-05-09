package com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip

@Composable
fun DocumentFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var documentType by remember {
        mutableStateOf("")
    }

    var dueDate by remember {
        mutableStateOf("")
    }

    var notes by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        TextButton(
            onClick = onBackClick
        ) {
            Text(text = "< Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Registrar documento",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        DocumentVehicleHeader(vehicle = vehicle)

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Datos del documento",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = documentType,
                    onValueChange = { newValue ->
                        documentType = newValue
                    },
                    label = {
                        Text(text = "Tipo de documento")
                    },
                    placeholder = {
                        Text(text = "Ej: SOAT, tecnicomecánica o impuestos")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = dueDate,
                    onValueChange = { newValue ->
                        dueDate = newValue
                    },
                    label = {
                        Text(text = "Fecha de vencimiento")
                    },
                    placeholder = {
                        Text(text = "Ej: 2026-05-21")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = notes,
                    onValueChange = { newValue ->
                        notes = newValue
                    },
                    label = {
                        Text(text = "Notas")
                    },
                    placeholder = {
                        Text(text = "Ej: Documento obligatorio del vehículo")
                    },
                    minLines = 3
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO: Guardar documento cuando implementemos almacenamiento
            }
        ) {
            Text(text = "Guardar documento")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onBackClick
        ) {
            Text(text = "Cancelar")
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun DocumentVehicleHeader(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                Text(
                    text = vehicle.plate,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${vehicle.brand} ${vehicle.model}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                VehicleTypeChip(type = vehicle.type)
            }
        }
    }
}
