package com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip
import kotlinx.coroutines.delay

@Composable
fun ExpenseFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var date by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf<ExpenseCategory?>(null)
    }

    var amount by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var showSaveMessage by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(showSaveMessage) {
        if (showSaveMessage) {
            delay(3500)
            showSaveMessage = false
        }
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
            text = "Registrar gasto",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        ExpenseVehicleHeader(vehicle = vehicle)

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
                    text = "Datos del gasto",
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
                        Text(text = "Ej: 2026-05-07")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Categoría",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExpenseCategoryOptions(
                    selectedCategory = category,
                    onCategoryClick = { selectedCategory ->
                        category = selectedCategory
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.all { character -> character.isDigit() }) {
                            amount = newValue
                        }
                    },
                    label = {
                        Text(text = "Valor")
                    },
                    placeholder = {
                        Text(text = "Ej: 65000")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
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
                        Text(text = "Ej: Tanqueo diario")
                    },
                    minLines = 2
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                showSaveMessage = true
                // TODO: Guardar gasto cuando implementemos almacenamiento
            }
        ) {
            Text(text = "Guardar gasto")
        }

        if (showSaveMessage) {
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = "El formulario está listo visualmente. El guardado se conectará cuando implementemos almacenamiento.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
fun ExpenseCategoryOptions(
    selectedCategory: ExpenseCategory?,
    onCategoryClick: (ExpenseCategory) -> Unit
) {
    val rows = ExpenseCategory.entries.chunked(2)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowCategories.forEach { category ->
                    ExpenseCategoryButton(
                        text = getExpenseCategoryLabel(category),
                        selected = selectedCategory == category,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onCategoryClick(category)
                        }
                    )
                }

                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ExpenseCategoryButton(
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

@Composable
fun ExpenseVehicleHeader(vehicle: Vehicle) {
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
