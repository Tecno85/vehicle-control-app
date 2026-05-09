package com.ivanmadrid.vehiclecontrolapp.data.local.repository

import com.ivanmadrid.vehiclecontrolapp.data.local.dao.VehicleDocumentDao
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toDomain
import com.ivanmadrid.vehiclecontrolapp.data.local.mapper.toEntity
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VehicleDocumentLocalRepository(
    private val vehicleDocumentDao: VehicleDocumentDao
) {
    fun getDocumentsByVehicle(vehicleId: Int): Flow<List<VehicleDocument>> {
        return vehicleDocumentDao.getDocumentsByVehicle(vehicleId).map { documents ->
            documents.map { document -> document.toDomain() }
        }
    }

    fun getAllDocumentsByDueDate(): Flow<List<VehicleDocument>> {
        return vehicleDocumentDao.getAllDocumentsByDueDate().map { documents ->
            documents.map { document -> document.toDomain() }
        }
    }

    suspend fun insertDocument(document: VehicleDocument): Long {
        return vehicleDocumentDao.insertDocument(document.toEntity())
    }

    suspend fun updateDocument(document: VehicleDocument) {
        vehicleDocumentDao.updateDocument(document.toEntity())
    }

    suspend fun deleteDocument(document: VehicleDocument) {
        vehicleDocumentDao.deleteDocument(document.toEntity())
    }
}
