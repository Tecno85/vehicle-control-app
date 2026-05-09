package com.ivanmadrid.vehiclecontrolapp.utils

import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateFormatUtilsTest {
    @Test
    fun getDaysUntilLabel_returnsTodayLabelForCurrentDate() {
        val today = LocalDate.now().toString()

        val label = getDaysUntilLabel(today)

        assertEquals("Vence hoy", label)
    }

    @Test
    fun getDaysUntilLabel_returnsTomorrowLabelForNextDate() {
        val tomorrow = LocalDate.now().plusDays(1).toString()

        val label = getDaysUntilLabel(tomorrow)

        assertEquals("Vence mañana", label)
    }

    @Test
    fun getDaysUntilLabel_returnsExpiredLabelForPastDate() {
        val yesterday = LocalDate.now().minusDays(1).toString()

        val label = getDaysUntilLabel(yesterday)

        assertEquals("Vencido", label)
    }

    @Test
    fun getDaysUntilLabel_returnsReviewLabelForInvalidDate() {
        val label = getDaysUntilLabel("fecha-invalida")

        assertEquals("Fecha por revisar", label)
    }

    @Test
    fun sortDocumentsByDueDate_ordersNearestDatesFirstAndInvalidDatesLast() {
        val documents = listOf(
            testDocument(id = 1, dueDate = "fecha-invalida"),
            testDocument(id = 2, dueDate = "2026-06-10"),
            testDocument(id = 3, dueDate = "2026-05-13"),
        )

        val sortedDocuments = sortDocumentsByDueDate(documents)

        assertEquals(listOf(3, 2, 1), sortedDocuments.map { document -> document.id })
    }

    private fun testDocument(
        id: Int,
        dueDate: String
    ): VehicleDocument {
        return VehicleDocument(
            id = id,
            vehicleId = 1,
            type = VehicleDocumentType.SOAT,
            dueDate = dueDate,
            notes = null,
        )
    }
}
