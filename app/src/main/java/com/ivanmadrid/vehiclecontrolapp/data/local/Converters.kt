package com.ivanmadrid.vehiclecontrolapp.data.local

import androidx.room.TypeConverter
import com.ivanmadrid.vehiclecontrolapp.domain.model.ExpenseCategory
import com.ivanmadrid.vehiclecontrolapp.domain.model.IncomeAdjustmentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.NoveltyPriority
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocumentType
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleType

class Converters {
    @TypeConverter
    fun fromVehicleType(value: VehicleType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toVehicleType(value: String?): VehicleType? {
        return value?.let { VehicleType.valueOf(it) }
    }

    @TypeConverter
    fun fromExpenseCategory(value: ExpenseCategory?): String? {
        return value?.name
    }

    @TypeConverter
    fun toExpenseCategory(value: String?): ExpenseCategory? {
        return value?.let { ExpenseCategory.valueOf(it) }
    }

    @TypeConverter
    fun fromNoveltyPriority(value: NoveltyPriority?): String? {
        return value?.name
    }

    @TypeConverter
    fun toNoveltyPriority(value: String?): NoveltyPriority? {
        return value?.let { NoveltyPriority.valueOf(it) }
    }

    @TypeConverter
    fun fromIncomeAdjustmentType(value: IncomeAdjustmentType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toIncomeAdjustmentType(value: String?): IncomeAdjustmentType? {
        return value?.let { IncomeAdjustmentType.valueOf(it) }
    }

    @TypeConverter
    fun fromVehicleDocumentType(value: VehicleDocumentType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toVehicleDocumentType(value: String?): VehicleDocumentType? {
        return value?.let { VehicleDocumentType.valueOf(it) }
    }
}
