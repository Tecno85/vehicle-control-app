package com.ivanmadrid.vehiclecontrolapp.presentation.screens.expenses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
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
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getExpenseCategoryIcon
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.ValidationField
import com.ivanmadrid.vehiclecontrolapp.utils.getTodayIsoDate
import com.ivanmadrid.vehiclecontrolapp.utils.validateExpenseForm

@Composable
fun ExpenseFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    expenseToEdit: Expense? = null,
    onSaveExpense: (Expense, (String) -> Unit) -> Unit,
    onBackClick: () -> Unit
) {
    var date by remember(expenseToEdit?.id) {
        mutableStateOf(expenseToEdit?.date ?: getTodayIsoDate())
    }

    var category by remember(expenseToEdit?.id) {
        mutableStateOf(expenseToEdit?.category)
    }

    var amount by remember(expenseToEdit?.id) {
        mutableStateOf(expenseToEdit?.amount?.toString().orEmpty())
    }

    var description by remember(expenseToEdit?.id) {
        mutableStateOf(expenseToEdit?.description.orEmpty())
    }

    var validationMessage by remember(expenseToEdit?.id) {
        mutableStateOf<String?>(null)
    }

    var validationField by remember(expenseToEdit?.id) {
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
            text = if (expenseToEdit == null) {
                "Registrar gasto"
            } else {
                "Editar gasto"
            },
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
                        if (validationField == ValidationField.CATEGORY) validationField = null
                    }
                )

                if (validationField == ValidationField.CATEGORY) {
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
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.all { character -> character.isDigit() }) {
                            amount = newValue
                            if (validationField == ValidationField.AMOUNT) validationField = null
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
                    supportingText = if (validationField == ValidationField.AMOUNT) {
                        { Text(text = validationMessage.orEmpty()) }
                    } else {
                        null
                    },
                    isError = validationField == ValidationField.AMOUNT,
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
                val selectedCategory = category
                val parsedAmount = amount.toLongOrNull()
                val validationResult = validateExpenseForm(
                    date = date,
                    category = selectedCategory,
                    amount = parsedAmount
                )

                if (!validationResult.isValid || selectedCategory == null || parsedAmount == null) {
                    validationMessage = validationResult.message
                    validationField = validationResult.field
                    return@Button
                }

                validationMessage = null
                validationField = null

                onSaveExpense(
                    Expense(
                        id = expenseToEdit?.id ?: 0,
                        vehicleId = vehicle.id,
                        date = date.trim(),
                        category = selectedCategory,
                        amount = parsedAmount,
                        description = description.trim().ifBlank { "Gasto registrado" }
                    )
                ) { message ->
                    validationMessage = message
                    validationField = null
                }
            }
        ) {
            Text(
                text = if (expenseToEdit == null) {
                    "Guardar gasto"
                } else {
                    "Actualizar gasto"
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
                        category = category,
                        iconRes = getExpenseCategoryIcon(category),
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
    category: ExpenseCategory,
    iconRes: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.vehicleColors
    val accentColor = getExpenseCategoryColor(category)
    val selectedBackgroundColor = getExpenseCategoryBackgroundColor(category)
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
            .heightIn(min = 74.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 1.dp else 0.dp)
    ) {
        CategoryButtonContent(
            text = getExpenseCategoryLabel(category),
            iconRes = iconRes,
            tint = accentColor,
            backgroundColor = if (selected) {
                MaterialTheme.colorScheme.surface
            } else {
                colors.avatarSurface
            }
        )
    }
}

@Composable
fun CategoryButtonContent(
    text: String,
    iconRes: Int,
    tint: Color,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = backgroundColor,
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(tint)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = tint,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
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
fun getExpenseCategoryColor(category: ExpenseCategory): Color {
    val colors = MaterialTheme.vehicleColors
    return when (category) {
        ExpenseCategory.FUEL,
        ExpenseCategory.WASH,
        ExpenseCategory.MAINTENANCE,
        ExpenseCategory.SPARE_PARTS,
        ExpenseCategory.INSURANCE,
        ExpenseCategory.OTHER -> colors.blue
        ExpenseCategory.TAXES -> colors.green
        ExpenseCategory.FINES -> colors.red
    }
}

@Composable
fun getExpenseCategoryBackgroundColor(category: ExpenseCategory): Color {
    val colors = MaterialTheme.vehicleColors
    return when (category) {
        ExpenseCategory.FUEL,
        ExpenseCategory.WASH,
        ExpenseCategory.MAINTENANCE,
        ExpenseCategory.SPARE_PARTS,
        ExpenseCategory.INSURANCE,
        ExpenseCategory.OTHER -> colors.softBlue
        ExpenseCategory.TAXES -> colors.softGreen
        ExpenseCategory.FINES -> colors.softRed
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
            VehicleAvatar(vehicle = vehicle)

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
