package com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip

@Composable
fun NoveltyFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var date by remember {
        mutableStateOf("")
    }

    var noveltyType by remember {
        mutableStateOf("")
    }

    var priority by remember {
        mutableStateOf<NoveltyPriority?>(null)
    }

    var description by remember {
        mutableStateOf("")
    }

    var affectsIncome by remember {
        mutableStateOf(false)
    }

    var incomeAdjustmentType by remember {
        mutableStateOf<IncomeAdjustmentType?>(null)
    }

    var adjustedIncomeAmount by remember {
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
            text = "Registrar novedad",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        NoveltyVehicleHeader(vehicle = vehicle)

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
                    text = "Datos de la novedad",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = date,
                    onValueChange = { newValue ->
                        date = newValue
                    },
                    label = {
                        Text(text = "Fecha")
                    },
                    placeholder = {
                        Text(text = "Ej: 2026-05-09")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = noveltyType,
                    onValueChange = { newValue ->
                        noveltyType = newValue
                    },
                    label = {
                        Text(text = "Tipo de novedad")
                    },
                    placeholder = {
                        Text(text = "Ej: Trabajo parcial")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Prioridad",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                PriorityOptions(
                    selectedPriority = priority,
                    onPriorityClick = { selectedPriority ->
                        priority = selectedPriority
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { newValue ->
                        description = newValue
                    },
                    label = {
                        Text(text = "Descripción")
                    },
                    placeholder = {
                        Text(text = "Ej: El taxi trabajó solo medio día")
                    },
                    minLines = 3
                )
            }
        }

        if (vehicle.type == VehicleType.TAXI) {
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
                        text = "Ajuste de ingreso",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = affectsIncome,
                            onCheckedChange = { checked ->
                                affectsIncome = checked
                            }
                        )

                        Text(
                            text = "Esta novedad afecta el ingreso del taxi",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    if (affectsIncome) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tipo de ajuste",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        IncomeAdjustmentOptions(
                            selectedType = incomeAdjustmentType,
                            onTypeClick = { selectedType ->
                                incomeAdjustmentType = selectedType
                            }
                        )

                        if (incomeAdjustmentType == IncomeAdjustmentType.CUSTOM_AMOUNT) {
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = adjustedIncomeAmount,
                                onValueChange = { newValue ->
                                    if (newValue.all { character -> character.isDigit() }) {
                                        adjustedIncomeAmount = newValue
                                    }
                                },
                                label = {
                                    Text(text = "Valor personalizado")
                                },
                                placeholder = {
                                    Text(text = "Ej: 90000")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // TODO: Guardar novedad cuando implementemos almacenamiento
            }
        ) {
            Text(text = "Guardar novedad")
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
fun PriorityOptions(
    selectedPriority: NoveltyPriority?,
    onPriorityClick: (NoveltyPriority) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NoveltyPriority.entries.forEach { priority ->
            OptionButton(
                text = getPriorityLabel(priority),
                selected = selectedPriority == priority,
                modifier = Modifier.weight(1f),
                onClick = {
                    onPriorityClick(priority)
                }
            )
        }
    }
}

@Composable
fun IncomeAdjustmentOptions(
    selectedType: IncomeAdjustmentType?,
    onTypeClick: (IncomeAdjustmentType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IncomeAdjustmentType.entries.forEach { type ->
            OptionButton(
                text = getIncomeAdjustmentLabel(type),
                selected = selectedType == type,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onTypeClick(type)
                }
            )
        }
    }
}

@Composable
fun OptionButton(
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

fun getPriorityLabel(priority: NoveltyPriority): String {
    return when (priority) {
        NoveltyPriority.LOW -> "Baja"
        NoveltyPriority.MEDIUM -> "Media"
        NoveltyPriority.HIGH -> "Alta"
    }
}

fun getIncomeAdjustmentLabel(type: IncomeAdjustmentType): String {
    return when (type) {
        IncomeAdjustmentType.NO_INCOME -> "Sin ingreso"
        IncomeAdjustmentType.HALF_INCOME -> "Medio ingreso"
        IncomeAdjustmentType.CUSTOM_AMOUNT -> "Valor personalizado"
    }
}

@Composable
fun NoveltyVehicleHeader(vehicle: Vehicle) {
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
