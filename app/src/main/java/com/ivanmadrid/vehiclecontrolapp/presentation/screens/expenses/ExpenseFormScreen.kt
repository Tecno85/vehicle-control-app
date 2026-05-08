package com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle

@Composable
fun ExpenseFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onBackClick
        ) {
            Text(text = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registrar gasto",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "${vehicle.plate} - ${vehicle.brand} ${vehicle.model}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {
                // TODO: Capturar fecha
            },
            label = {
                Text(text = "Fecha")
            },
            placeholder = {
                Text(text = "Ej: 2026-05-07")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {
                // TODO: Capturar categoría
            },
            label = {
                Text(text = "Categoría")
            },
            placeholder = {
                Text(text = "Ej: Combustible")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {
                // TODO: Capturar valor
            },
            label = {
                Text(text = "Valor")
            },
            placeholder = {
                Text(text = "Ej: 65000")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {
                // TODO: Capturar descripción
            },
            label = {
                Text(text = "Descripción")
            },
            placeholder = {
                Text(text = "Ej: Tanqueo diario")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO: Guardar gasto
            }
        ) {
            Text(text = "Guardar gasto")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onBackClick
        ) {
            Text(text = "Cancelar")
        }
    }
}