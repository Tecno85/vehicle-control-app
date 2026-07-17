package com.ivanmadrid.vehiclecontrolapp.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.R
import com.ivanmadrid.vehiclecontrolapp.utils.formatIsoDateForDisplay
import java.time.LocalDate

@Composable
fun FormActionBar(
    primaryText: String,
    onPrimaryClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AppSecondaryButton(
                text = "Cancelar",
                modifier = Modifier.weight(1f),
                onClick = onCancelClick
            )

            AppPrimaryButton(
                text = primaryText,
                modifier = Modifier.weight(1.35f),
                onClick = onPrimaryClick
            )
        }
    }
}

@Composable
fun AppDateField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val selectedDate = runCatching {
        LocalDate.parse(value)
    }.getOrDefault(LocalDate.now())

    val openDatePicker = {
        DatePickerDialog(
            context,
            if (isDarkTheme) {
                R.style.Theme_VehicleControlApp_DatePicker_Dark
            } else {
                R.style.Theme_VehicleControlApp_DatePicker_Light
            },
            { _, year, month, dayOfMonth ->
                onValueChange(
                    LocalDate.of(year, month + 1, dayOfMonth).toString()
                )
            },
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        ).show()
    }

    OutlinedTextField(
        modifier = modifier.clickable(onClick = openDatePicker),
        value = formatIsoDateForDisplay(value),
        onValueChange = {},
        readOnly = true,
        label = {
            Text(text = label)
        },
        supportingText = {
            Text(
                text = if (isError) {
                    errorMessage.orEmpty()
                } else {
                    "Selecciona una fecha"
                }
            )
        },
        trailingIcon = {
            TextButton(onClick = openDatePicker) {
                Text(text = "Cambiar")
            }
        },
        isError = isError,
        singleLine = true
    )
}
