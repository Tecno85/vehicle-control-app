package com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.presentation.components.AppBackButton
import com.ivanmadrid.vehiclecontrolapp.presentation.components.getVehicleDocumentIcon
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleAvatar
import com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles.VehicleTypeChip
import com.ivanmadrid.vehiclecontrolapp.ui.theme.vehicleColors
import com.ivanmadrid.vehiclecontrolapp.utils.validateDocumentForm

@Composable
fun DocumentFormScreen(
    vehicle: Vehicle,
    modifier: Modifier = Modifier,
    documentToEdit: VehicleDocument? = null,
    onSaveDocument: (VehicleDocument) -> Unit,
    onBackClick: () -> Unit
) {
    var documentType by remember(documentToEdit?.id) {
        mutableStateOf(documentToEdit?.type)
    }

    var dueDate by remember(documentToEdit?.id) {
        mutableStateOf(documentToEdit?.dueDate.orEmpty())
    }

    var notes by remember(documentToEdit?.id) {
        mutableStateOf(documentToEdit?.notes.orEmpty())
    }

    var validationMessage by remember(documentToEdit?.id) {
        mutableStateOf<String?>(null)
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
            text = if (documentToEdit == null) {
                "Registrar documento"
            } else {
                "Editar documento"
            },
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

                Text(
                    text = "Tipo de documento",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                DocumentTypeOptions(
                    selectedType = documentType,
                    onTypeClick = { selectedType ->
                        documentType = selectedType
                    }
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
                    supportingText = {
                        Text(text = "Formato: yyyy-MM-dd")
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
                val selectedDocumentType = documentType
                val validationResult = validateDocumentForm(
                    documentType = selectedDocumentType,
                    dueDate = dueDate
                )

                if (!validationResult.isValid || selectedDocumentType == null) {
                    validationMessage = validationResult.message
                    return@Button
                }

                validationMessage = null

                onSaveDocument(
                    VehicleDocument(
                        id = documentToEdit?.id ?: 0,
                        vehicleId = vehicle.id,
                        type = selectedDocumentType,
                        dueDate = dueDate.trim(),
                        notes = notes.trim().ifBlank { null }
                    )
                )
            }
        ) {
            Text(
                text = if (documentToEdit == null) {
                    "Guardar documento"
                } else {
                    "Actualizar documento"
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
fun DocumentTypeOptions(
    selectedType: VehicleDocumentType?,
    onTypeClick: (VehicleDocumentType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        VehicleDocumentType.entries.forEach { type ->
            DocumentTypeButton(
                type = type,
                iconRes = getVehicleDocumentIcon(type),
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
fun DocumentTypeButton(
    type: VehicleDocumentType,
    iconRes: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val accentColor = getDocumentTypeColor(type)
    val selectedBackgroundColor = getDocumentTypeBackgroundColor(type)
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
            .heightIn(min = 68.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 1.dp else 0.dp)
    ) {
        DocumentTypeButtonContent(
            title = getDocumentTypeLabel(type),
            description = getDocumentTypeDescription(type),
            iconRes = iconRes,
            tint = accentColor,
            backgroundColor = if (selected) {
                MaterialTheme.colorScheme.surface
            } else {
                getDocumentTypeBackgroundColor(type)
            }
        )
    }
}

@Composable
fun DocumentTypeButtonContent(
    title: String,
    description: String,
    iconRes: Int,
    tint: Color,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(21.dp),
                colorFilter = ColorFilter.tint(tint)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = tint,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

fun getDocumentTypeLabel(type: VehicleDocumentType): String {
    return when (type) {
        VehicleDocumentType.SOAT -> "SOAT"
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> "Tecnicomecánica"
        VehicleDocumentType.TAXES -> "Impuestos"
    }
}

fun getDocumentTypeDescription(type: VehicleDocumentType): String {
    return when (type) {
        VehicleDocumentType.SOAT -> "Seguro obligatorio"
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> "Revisión del vehículo"
        VehicleDocumentType.TAXES -> "Pago anual"
    }
}

@Composable
fun getDocumentTypeColor(type: VehicleDocumentType): Color {
    val colors = MaterialTheme.vehicleColors
    return when (type) {
        VehicleDocumentType.SOAT -> colors.orange
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> colors.red
        VehicleDocumentType.TAXES -> colors.green
    }
}

@Composable
fun getDocumentTypeBackgroundColor(type: VehicleDocumentType): Color {
    val colors = MaterialTheme.vehicleColors
    return when (type) {
        VehicleDocumentType.SOAT -> colors.softYellow
        VehicleDocumentType.TECHNICAL_MECHANICAL_REVIEW -> colors.softRed
        VehicleDocumentType.TAXES -> colors.softGreen
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
