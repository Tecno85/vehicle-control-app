package com.ivanmadrid.vehiclecontrolapp.utils

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
