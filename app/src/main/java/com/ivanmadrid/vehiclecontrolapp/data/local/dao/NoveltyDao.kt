package com.ivanmadrid.vehiclecontrolapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ivanmadrid.vehiclecontrolapp.data.local.entity.NoveltyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoveltyDao {
    @Query("SELECT * FROM novelties WHERE vehicleId = :vehicleId ORDER BY date DESC")
    fun getNoveltiesByVehicle(vehicleId: Int): Flow<List<NoveltyEntity>>

    @Insert
    suspend fun insertNovelty(novelty: NoveltyEntity): Long

    @Update
    suspend fun updateNovelty(novelty: NoveltyEntity)

    @Delete
    suspend fun deleteNovelty(novelty: NoveltyEntity)

    @Query("DELETE FROM novelties WHERE vehicleId = :vehicleId")
    suspend fun deleteNoveltiesByVehicle(vehicleId: Int)
}
