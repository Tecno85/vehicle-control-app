package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.ValidationField
import com.ivanmadrid.vehiclecontrolapp.utils.validateVehicleForm

@Composable
fun VehicleFormScreen(
    modifier: Modifier = Modifier,
    vehicleToEdit: Vehicle? = null,
    onSaveVehicle: (Vehicle, (String) -> Unit) -> Unit,
    onBackClick: () -> Unit
) {
    var plate by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.plate.orEmpty())
    }

    var brand by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.brand.orEmpty())
    }

    var model by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.model.orEmpty())
    }

    var vehicleType by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.type)
    }

    var status by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.status ?: "Activo")
    }

    var currentDriver by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.currentDriver.orEmpty())
    }

    var dailyFixedIncome by remember(vehicleToEdit?.id) {
        mutableStateOf(vehicleToEdit?.dailyFixedIncome?.toString().orEmpty())
    }

    var validationMessage by remember(vehicleToEdit?.id) {
        mutableStateOf<String?>(null)
    }

    var validationField by remember(vehicleToEdit?.id) {
        mutableStateOf<ValidationField?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        AppBackButton(onClick = onBackClick)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (vehicleToEdit == null) {
                "Agregar vehículo"
            } else {
                "Editar vehículo"
            },
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (vehicleToEdit == null) {
                "Registra la información básica del vehículo."
            } else {
                "Actualiza la información básica del vehículo."
            },
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
                        if (validationField == ValidationField.PLATE) validationField = null
                    },
                    label = {
                        Text(text = "Placa")
                    },
                    placeholder = {
                        Text(text = "Ej: ABC123")
                    },
                    supportingText = {
                        Text(
                            text = if (validationField == ValidationField.PLATE) {
                                validationMessage.orEmpty()
                            } else {
                                "Se guardará en mayúsculas."
                            }
                        )
                    },
                    isError = validationField == ValidationField.PLATE,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = brand,
                    onValueChange = { newValue ->
                        brand = newValue
                        if (validationField == ValidationField.BRAND) validationField = null
                    },
                    label = {
                        Text(text = "Marca")
                    },
                    placeholder = {
                        Text(text = "Ej: Kia")
                    },
                    supportingText = if (validationField == ValidationField.BRAND) {
                        { Text(text = validationMessage.orEmpty()) }
                    } else {
                        null
                    },
                    isError = validationField == ValidationField.BRAND,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = model,
                    onValueChange = { newValue ->
                        model = newValue
                        if (validationField == ValidationField.MODEL) validationField = null
                    },
                    label = {
                        Text(text = "Modelo")
                    },
                    placeholder = {
                        Text(text = "Ej: Picanto")
                    },
                    supportingText = if (validationField == ValidationField.MODEL) {
                        { Text(text = validationMessage.orEmpty()) }
                    } else {
                        null
                    },
                    isError = validationField == ValidationField.MODEL,
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    VehicleTypeOptionButton(
                        type = VehicleType.TAXI,
                        selected = vehicleType == VehicleType.TAXI,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            vehicleType = VehicleType.TAXI
                            if (validationField == ValidationField.VEHICLE_TYPE) validationField = null
                        }
                    )

                    VehicleTypeOptionButton(
                        type = VehicleType.PRIVATE,
                        selected = vehicleType == VehicleType.PRIVATE,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            vehicleType = VehicleType.PRIVATE
                            if (validationField == ValidationField.VEHICLE_TYPE) validationField = null
                        }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (vehicleType == VehicleType.TAXI) {
                        "Los taxis permiten registrar conductor actual e ingreso diario."
                    } else if (vehicleType == VehicleType.PRIVATE) {
                        "Los particulares no manejan ingreso diario."
                    } else {
                        "Selecciona Taxi o Particular para continuar."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (validationField == ValidationField.VEHICLE_TYPE) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )

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
                        text = "Información del taxi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Estos campos solo aplican para vehículos tipo taxi.",
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
                                if (validationField == ValidationField.DAILY_FIXED_INCOME) {
                                    validationField = null
                                }
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
                        supportingText = if (validationField == ValidationField.DAILY_FIXED_INCOME) {
                            { Text(text = validationMessage.orEmpty()) }
                        } else {
                            null
                        },
                        isError = validationField == ValidationField.DAILY_FIXED_INCOME,
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
                val parsedDailyFixedIncome = dailyFixedIncome.toLongOrNull()
                val validationResult = validateVehicleForm(
                    plate = plate,
                    brand = brand,
                    model = model,
                    vehicleType = selectedType,
                    dailyFixedIncome = parsedDailyFixedIncome
                )

                if (!validationResult.isValid || selectedType == null) {
                    validationMessage = validationResult.message
                    validationField = validationResult.field
                    return@Button
                }

                validationMessage = null
                validationField = null

                onSaveVehicle(
                    Vehicle(
                        id = vehicleToEdit?.id ?: 0,
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
                            parsedDailyFixedIncome
                        } else {
                            null
                        }
                    )
                ) { message ->
                    validationMessage = message
                    validationField = if (message.contains("placa", ignoreCase = true)) {
                        ValidationField.PLATE
                    } else {
                        null
                    }
                }
            }
        ) {
            Text(
                text = if (vehicleToEdit == null) {
                    "Guardar vehículo"
                } else {
                    "Actualizar vehículo"
                }
            )
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
    type: VehicleType,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val label = getVehicleTypeLabel(type)
    val accentColor = when (type) {
        VehicleType.TAXI -> colors.orange
        VehicleType.PRIVATE -> colors.green
    }
    val selectedContainerColor = when (type) {
        VehicleType.TAXI -> colors.softYellow
        VehicleType.PRIVATE -> colors.softGreen
    }
    val containerColor = if (selected) {
        selectedContainerColor
    } else {
        MaterialTheme.colorScheme.surface
    }
    val borderColor = if (selected) {
        accentColor.copy(alpha = 0.55f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    }

    Card(
        modifier = modifier
            .heightIn(min = 112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 1.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VehicleAvatar(type = type, size = 58)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = accentColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = if (type == VehicleType.TAXI) {
                    "Con ingreso diario"
                } else {
                    "Uso particular"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
