package com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.ValidationField
import com.ivanmadrid.vehiclecontrolapp.utils.getTodayIsoDate
import com.ivanmadrid.vehiclecontrolapp.utils.validateNoveltyForm

@Composable
fun NoveltyFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    noveltyToEdit: Novelty? = null,
    onSaveNovelty: (Novelty, (String) -> Unit) -> Unit,
    onBackClick: () -> Unit
) {
    var date by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.date ?: getTodayIsoDate())
    }

    var noveltyType by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.type.orEmpty())
    }

    var priority by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.priority)
    }

    var description by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.description.orEmpty())
    }

    var affectsIncome by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.affectsIncome ?: false)
    }

    var incomeAdjustmentType by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.incomeAdjustmentType)
    }

    var adjustedIncomeAmount by remember(noveltyToEdit?.id) {
        mutableStateOf(noveltyToEdit?.adjustedIncomeAmount?.toString().orEmpty())
    }

    var validationMessage by remember(noveltyToEdit?.id) {
        mutableStateOf<String?>(null)
    }

    var validationField by remember(noveltyToEdit?.id) {
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
            text = if (noveltyToEdit == null) {
                "Registrar novedad"
            } else {
                "Editar novedad"
            },
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
                        if (validationField == ValidationField.DATE) validationField = null
                    },
                    label = {
                        Text(text = "Fecha")
                    },
                    placeholder = {
                        Text(text = "Ej: 2026-07-16")
                    },
                    supportingText = {
                        Text(
                            text = if (validationField == ValidationField.DATE) {
                                validationMessage.orEmpty()
                            } else {
                                "Formato: yyyy-MM-dd"
                            }
                        )
                    },
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                date = getTodayIsoDate()
                                if (validationField == ValidationField.DATE) validationField = null
                            }
                        ) {
                            Text(text = "Hoy")
                        }
                    },
                    isError = validationField == ValidationField.DATE,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = noveltyType,
                    onValueChange = { newValue ->
                        noveltyType = newValue
                        if (validationField == ValidationField.NOVELTY_TYPE) validationField = null
                    },
                    label = {
                        Text(text = "Tipo de novedad")
                    },
                    placeholder = {
                        Text(text = "Ej: Trabajo parcial")
                    },
                    supportingText = if (validationField == ValidationField.NOVELTY_TYPE) {
                        { Text(text = validationMessage.orEmpty()) }
                    } else {
                        null
                    },
                    isError = validationField == ValidationField.NOVELTY_TYPE,
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
                        if (validationField == ValidationField.PRIORITY) validationField = null
                    }
                )

                if (validationField == ValidationField.PRIORITY) {
                    Text(
                        text = validationMessage.orEmpty(),
                        modifier = Modifier.padding(top = 6.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

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
                        text = "Impacto en la operación",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    IncomeImpactToggle(
                        checked = affectsIncome,
                        onCheckedChange = { checked ->
                            affectsIncome = checked
                            if (!checked) {
                                validationField = null
                            }
                        }
                    )

                    if (affectsIncome) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Resultado del día",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        IncomeAdjustmentOptions(
                            selectedType = incomeAdjustmentType,
                            onTypeClick = { selectedType ->
                                incomeAdjustmentType = selectedType
                                if (validationField == ValidationField.INCOME_ADJUSTMENT_TYPE) {
                                    validationField = null
                                }
                            }
                        )

                        if (validationField == ValidationField.INCOME_ADJUSTMENT_TYPE) {
                            Text(
                                text = validationMessage.orEmpty(),
                                modifier = Modifier.padding(top = 6.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        if (incomeAdjustmentType == IncomeAdjustmentType.CUSTOM_AMOUNT) {
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = adjustedIncomeAmount,
                                onValueChange = { newValue ->
                                    if (newValue.all { character -> character.isDigit() }) {
                                        adjustedIncomeAmount = newValue
                                        if (validationField == ValidationField.ADJUSTED_INCOME_AMOUNT) {
                                            validationField = null
                                        }
                                    }
                                },
                                label = {
                                    Text(text = "Ingreso real del día")
                                },
                                placeholder = {
                                    Text(text = "Ej: 90000")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                supportingText = if (
                                    validationField == ValidationField.ADJUSTED_INCOME_AMOUNT
                                ) {
                                    { Text(text = validationMessage.orEmpty()) }
                                } else {
                                    null
                                },
                                isError = validationField == ValidationField.ADJUSTED_INCOME_AMOUNT,
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
                val selectedPriority = priority
                val selectedAdjustmentType = incomeAdjustmentType
                val parsedAdjustedIncome = adjustedIncomeAmount.toLongOrNull()
                val shouldAffectIncome = vehicle.type == VehicleType.TAXI && affectsIncome
                val validationResult = validateNoveltyForm(
                    date = date,
                    noveltyType = noveltyType,
                    priority = selectedPriority,
                    affectsIncome = shouldAffectIncome,
                    incomeAdjustmentType = selectedAdjustmentType,
                    adjustedIncomeAmount = parsedAdjustedIncome
                )

                if (!validationResult.isValid || selectedPriority == null) {
                    validationMessage = validationResult.message
                    validationField = validationResult.field
                    return@Button
                }

                validationMessage = null
                validationField = null

                onSaveNovelty(
                    Novelty(
                        id = noveltyToEdit?.id ?: 0,
                        vehicleId = vehicle.id,
                        date = date.trim(),
                        type = noveltyType.trim(),
                        description = description.trim().ifBlank { noveltyType.trim() },
                        priority = selectedPriority,
                        affectsIncome = shouldAffectIncome,
                        incomeAdjustmentType = if (shouldAffectIncome) {
                            selectedAdjustmentType
                        } else {
                            null
                        },
                        adjustedIncomeAmount = if (
                            shouldAffectIncome &&
                            selectedAdjustmentType == IncomeAdjustmentType.CUSTOM_AMOUNT
                        ) {
                            parsedAdjustedIncome
                        } else {
                            null
                        }
                    )
                ) { message ->
                    validationMessage = message
                    validationField = null
                }
            }
        ) {
            Text(
                text = if (noveltyToEdit == null) {
                    "Guardar novedad"
                } else {
                    "Actualizar novedad"
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
fun PriorityOptions(
    selectedPriority: NoveltyPriority?,
    onPriorityClick: (NoveltyPriority) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NoveltyPriority.entries.forEach { priority ->
            PriorityOptionButton(
                priority = priority,
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
            IncomeAdjustmentOptionButton(
                type = type,
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
fun PriorityOptionButton(
    priority: NoveltyPriority,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val accentColor = when (priority) {
        NoveltyPriority.LOW -> colors.green
        NoveltyPriority.MEDIUM -> colors.orange
        NoveltyPriority.HIGH -> colors.red
    }
    val backgroundColor = when (priority) {
        NoveltyPriority.LOW -> colors.softGreen
        NoveltyPriority.MEDIUM -> colors.softYellow
        NoveltyPriority.HIGH -> colors.softRed
    }
    val description = when (priority) {
        NoveltyPriority.LOW -> "Seguimiento"
        NoveltyPriority.MEDIUM -> "Revisar"
        NoveltyPriority.HIGH -> "Prioritaria"
    }

    NoveltyOptionCard(
        title = getPriorityLabel(priority),
        description = description,
        iconRes = getPriorityIcon(priority),
        selected = selected,
        accentColor = accentColor,
        selectedBackgroundColor = backgroundColor,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun IncomeAdjustmentOptionButton(
    type: IncomeAdjustmentType,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val description = when (type) {
        IncomeAdjustmentType.NO_INCOME -> "Ingreso $0"
        IncomeAdjustmentType.HALF_INCOME -> "Aprox. 50%"
        IncomeAdjustmentType.CUSTOM_AMOUNT -> "Valor real"
    }

    NoveltyOptionCard(
        title = getIncomeAdjustmentLabel(type),
        description = description,
        iconRes = R.drawable.ic_detail_expense,
        selected = selected,
        accentColor = colors.green,
        selectedBackgroundColor = colors.softGreen,
        modifier = modifier,
        horizontal = true,
        onClick = onClick
    )
}

@Composable
fun NoveltyOptionCard(
    title: String,
    description: String,
    iconRes: Int,
    selected: Boolean,
    accentColor: Color,
    selectedBackgroundColor: Color,
    modifier: Modifier = Modifier,
    horizontal: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor = if (selected) {
        selectedBackgroundColor
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
            .heightIn(min = if (horizontal) 64.dp else 88.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 1.dp else 0.dp)
    ) {
        if (horizontal) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NoveltyOptionIcon(
                    iconRes = iconRes,
                    color = accentColor,
                    backgroundColor = selectedBackgroundColor
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoveltyOptionIcon(
                    iconRes = iconRes,
                    color = accentColor,
                    backgroundColor = selectedBackgroundColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun NoveltyOptionIcon(
    iconRes: Int,
    color: Color,
    backgroundColor: Color
) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .size(34.dp)
            .background(
                color = backgroundColor,
                shape = androidx.compose.foundation.shape.CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(19.dp),
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@Composable
fun IncomeImpactToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colors = MaterialTheme.vehicleColors

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!checked)
            },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (checked) {
                colors.orange.copy(alpha = 0.55f)
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (checked) {
                colors.softYellow
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Cambió el día de trabajo del taxi",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Activa esta opción si el ingreso esperado del día fue diferente.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
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

fun getPriorityIcon(priority: NoveltyPriority): Int {
    return when (priority) {
        NoveltyPriority.LOW -> R.drawable.ic_priority_low
        NoveltyPriority.MEDIUM -> R.drawable.ic_priority_medium
        NoveltyPriority.HIGH -> R.drawable.ic_priority_high
    }
}

fun getIncomeAdjustmentLabel(type: IncomeAdjustmentType): String {
    return when (type) {
        IncomeAdjustmentType.NO_INCOME -> "No trabajó"
        IncomeAdjustmentType.HALF_INCOME -> "Trabajó medio día"
        IncomeAdjustmentType.CUSTOM_AMOUNT -> "Trabajó con ingreso diferente"
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
