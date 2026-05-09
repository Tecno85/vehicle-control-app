package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

@Composable
fun VehicleFormScreen(
    modifier: Modifier = Modifier,
    onSaveVehicle: (Vehicle) -> Unit,
    onBackClick: () -> Unit
) {
    var plate by remember {
        mutableStateOf("")
    }

    var brand by remember {
        mutableStateOf("")
    }

    var model by remember {
        mutableStateOf("")
    }

    var vehicleType by remember {
        mutableStateOf<VehicleType?>(null)
    }

    var status by remember {
        mutableStateOf("Activo")
    }

    var currentDriver by remember {
        mutableStateOf("")
    }

    var dailyFixedIncome by remember {
        mutableStateOf("")
    }

    var validationMessage by remember {
        mutableStateOf<String?>(null)
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
            text = "Agregar vehículo",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Registra la información básica del vehículo.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

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
                    text = "Datos del vehículo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = plate,
                    onValueChange = { newValue ->
                        plate = newValue.uppercase()
                    },
                    label = {
                        Text(text = "Placa")
                    },
                    placeholder = {
                        Text(text = "Ej: ABC123")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = brand,
                    onValueChange = { newValue ->
                        brand = newValue
                    },
                    label = {
                        Text(text = "Marca")
                    },
                    placeholder = {
                        Text(text = "Ej: Kia")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = model,
                    onValueChange = { newValue ->
                        model = newValue
                    },
                    label = {
                        Text(text = "Modelo")
                    },
                    placeholder = {
                        Text(text = "Ej: Picanto")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Tipo de vehículo",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VehicleTypeOptionButton(
                        text = "Taxi",
                        selected = vehicleType == VehicleType.TAXI,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            vehicleType = VehicleType.TAXI
                        }
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    VehicleTypeOptionButton(
                        text = "Particular",
                        selected = vehicleType == VehicleType.PRIVATE,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            vehicleType = VehicleType.PRIVATE
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = status,
                    onValueChange = { newValue ->
                        status = newValue
                    },
                    label = {
                        Text(text = "Estado")
                    },
                    placeholder = {
                        Text(text = "Ej: Activo")
                    },
                    singleLine = true
                )
            }
        }

        if (vehicleType == VehicleType.TAXI) {
            Spacer(modifier = Modifier.height(12.dp))

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
                        text = "Datos de taxi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Información especial para vehículos tipo taxi.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentDriver,
                        onValueChange = { newValue ->
                            currentDriver = newValue
                        },
                        label = {
                            Text(text = "Conductor actual")
                        },
                        placeholder = {
                            Text(text = "Ej: Carlos Pérez")
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dailyFixedIncome,
                        onValueChange = { newValue ->
                            if (newValue.all { character -> character.isDigit() }) {
                                dailyFixedIncome = newValue
                            }
                        },
                        label = {
                            Text(text = "Ingreso diario")
                        },
                        placeholder = {
                            Text(text = "Ej: 180000")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val selectedType = vehicleType

                if (
                    plate.isBlank() ||
                    brand.isBlank() ||
                    model.isBlank() ||
                    selectedType == null
                ) {
                    validationMessage = "Completa placa, marca, modelo y tipo de vehículo."
                    return@Button
                }

                validationMessage = null

                onSaveVehicle(
                    Vehicle(
                        id = 0,
                        plate = plate.trim().uppercase(),
                        brand = brand.trim(),
                        model = model.trim(),
                        type = selectedType,
                        status = status.trim().ifBlank { "Activo" },
                        currentDriver = if (selectedType == VehicleType.TAXI) {
                            currentDriver.trim().ifBlank { null }
                        } else {
                            null
                        },
                        dailyFixedIncome = if (selectedType == VehicleType.TAXI) {
                            dailyFixedIncome.toLongOrNull()
                        } else {
                            null
                        }
                    )
                )
            }
        ) {
            Text(text = "Guardar vehículo")
        }

        validationMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
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
fun VehicleTypeOptionButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            modifier = modifier,
            onClick = onClick
        ) {
            Text(text = text)
        }
    } else {
        OutlinedButton(
            modifier = modifier,
            onClick = onClick
        ) {
            Text(text = text)
        }
    }
}
