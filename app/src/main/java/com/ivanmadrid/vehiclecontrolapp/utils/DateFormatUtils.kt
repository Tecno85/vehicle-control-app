package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

private val readableDateFormatter = DateTimeFormatter.ofPattern(
    "d MMM yyyy",
    Locale.forLanguageTag("es-CO")
)

fun formatIsoDateForDisplay(dateText: String): String {
    return runCatching {
        LocalDate.parse(dateText).format(readableDateFormatter)
    }.getOrDefault(dateText)
}

fun getDaysUntilLabel(dateText: String): String {
    return try {
        val daysUntil = getDaysUntilCount(dateText)

        when {
            daysUntil == null -> "Fecha por revisar"
            daysUntil < 0 -> "Vencido"
            daysUntil == 0L -> "Vence hoy"
            daysUntil == 1L -> "Vence mañana"
            else -> "Faltan $daysUntil días"
        }
    } catch (_: Exception) {
        "Fecha por revisar"
    }
}

fun getDaysUntilCount(dateText: String): Long? {
    return try {
        val dueDate = LocalDate.parse(dateText)
        ChronoUnit.DAYS.between(LocalDate.now(), dueDate)
    } catch (_: Exception) {
        null
    }
}

enum class DocumentUrgency {
    OVERDUE,
    URGENT,
    WARNING,
    NORMAL,
    UNKNOWN,
}

fun getDocumentUrgency(daysUntil: Long?): DocumentUrgency {
    return when {
        daysUntil == null -> DocumentUrgency.UNKNOWN
        daysUntil < 0 -> DocumentUrgency.OVERDUE
        daysUntil <= 7 -> DocumentUrgency.URGENT
        daysUntil <= 15 -> DocumentUrgency.WARNING
        else -> DocumentUrgency.NORMAL
    }
}

fun sortDocumentsByDueDate(documents: List<VehicleDocument>): List<VehicleDocument> {
    return documents.sortedWith(
        compareBy { document ->
            parseDateOrMax(document.dueDate)
        }
    )
}

fun isValidIsoDate(dateText: String): Boolean {
    return try {
        LocalDate.parse(dateText)
        true
    } catch (_: Exception) {
        false
    }
}

fun getTodayIsoDate(): String {
    return LocalDate.now().toString()
}

private fun parseDateOrMax(dateText: String): LocalDate {
    return try {
        LocalDate.parse(dateText)
    } catch (_: Exception) {
        LocalDate.MAX
    }
}
