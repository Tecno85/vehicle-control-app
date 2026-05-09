package com.ivanmadrid.vehiclecontrolapp.data.local

import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleExpenses
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleNovelties
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicleDocuments
import com.ivanmadrid.vehiclecontrolapp.data.sample.sampleVehicles

class DatabaseSeeder(
    private val database: AppDatabase
) {
    suspend fun seedIfEmpty() {
        val vehicleCount = database.vehicleDao().getVehicleCount()

        if (vehicleCount > 0) {
            return
        }

        sampleVehicles.forEach { vehicle ->
            database.vehicleDao().insertVehicle(vehicle.toEntity())
        }

        sampleExpenses.forEach { expense ->
            database.expenseDao().insertExpense(expense.toEntity())
        }

        sampleNovelties.forEach { novelty ->
            database.noveltyDao().insertNovelty(novelty.toEntity())
        }

        sampleVehicleDocuments.forEach { document ->
            database.vehicleDocumentDao().insertDocument(document.toEntity())
        }
    }
}
