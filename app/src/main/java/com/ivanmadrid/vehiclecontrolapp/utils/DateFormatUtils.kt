package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun getDaysUntilLabel(dateText: String): String {
    return try {
        val dueDate = LocalDate.parse(dateText)
        val daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), dueDate)

        when {
            daysUntil < 0 -> "Vencido"
            daysUntil == 0L -> "Vence hoy"
            daysUntil == 1L -> "Vence mañana"
            else -> "Faltan $daysUntil días"
        }
    } catch (_: Exception) {
        "Fecha por revisar"
    }
}

fun sortDocumentsByDueDate(documents: List<VehicleDocument>): List<VehicleDocument> {
    return documents.sortedWith(
        compareBy { document ->
            parseDateOrMax(document.dueDate)
        }
    )
}

private fun parseDateOrMax(dateText: String): LocalDate {
    return try {
        LocalDate.parse(dateText)
    } catch (_: Exception) {
        LocalDate.MAX
    }
}
