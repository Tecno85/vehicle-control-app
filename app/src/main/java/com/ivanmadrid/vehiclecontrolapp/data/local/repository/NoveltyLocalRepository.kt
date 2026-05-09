package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.NoveltyDao
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toDomain
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoveltyLocalRepository(
    private val noveltyDao: NoveltyDao
) {
    fun getNoveltiesByVehicle(vehicleId: Int): Flow<List<Novelty>> {
        return noveltyDao.getNoveltiesByVehicle(vehicleId).map { novelties ->
            novelties.map { novelty -> novelty.toDomain() }
        }
    }

    suspend fun insertNovelty(novelty: Novelty): Long {
        return noveltyDao.insertNovelty(novelty.toEntity())
    }

    suspend fun updateNovelty(novelty: Novelty) {
        noveltyDao.updateNovelty(novelty.toEntity())
    }

    suspend fun deleteNovelty(novelty: Novelty) {
        noveltyDao.deleteNovelty(novelty.toEntity())
    }
}
