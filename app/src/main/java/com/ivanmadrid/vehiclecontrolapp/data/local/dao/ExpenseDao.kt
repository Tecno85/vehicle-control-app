package com.ivanmadrid.vehiclecontrolapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE vehicleId = :vehicleId ORDER BY date DESC")
    fun getExpensesByVehicle(vehicleId: Int): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE vehicleId = :vehicleId")
    suspend fun deleteExpensesByVehicle(vehicleId: Int)
}
