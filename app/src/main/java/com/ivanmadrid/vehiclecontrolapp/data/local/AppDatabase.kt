package com.ivanmadrid.vehiclecontrolapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.ExpenseDao
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.NoveltyDao
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDao
import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDocumentDao
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleDocumentEntity
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.VehicleEntity

@Database(
    entities = [
        VehicleEntity::class,
        ExpenseEntity::class,
        NoveltyEntity::class,
        VehicleDocumentEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun noveltyDao(): NoveltyDao

    abstract fun vehicleDocumentDao(): VehicleDocumentDao
}
