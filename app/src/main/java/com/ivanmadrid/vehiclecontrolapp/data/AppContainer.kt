package com.ivanmadrid.vehiclecontrolapp.data

import android.content.Context
import androidx.room.Room
import com.ivanmadrid.vehiclecontrolapp.data.local.AppDatabase
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.ExpenseLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.NoveltyLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDocumentLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleLocalRepository

class AppContainer(
    context: Context
) {
    private val database: AppDatabase = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = "vehicle_control_database"
    ).build()

    val vehicleRepository = VehicleLocalRepository(
        vehicleDao = database.vehicleDao()
    )

    val expenseRepository = ExpenseLocalRepository(
        expenseDao = database.expenseDao()
    )

    val noveltyRepository = NoveltyLocalRepository(
        noveltyDao = database.noveltyDao()
    )

    val vehicleDocumentRepository = VehicleDocumentLocalRepository(
        vehicleDocumentDao = database.vehicleDocumentDao()
    )
}
