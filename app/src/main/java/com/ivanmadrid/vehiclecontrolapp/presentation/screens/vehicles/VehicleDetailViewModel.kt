package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.ExpenseLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.NoveltyLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDeletionRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDocumentLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Expense
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VehicleDetailViewModel(
    vehicleId: Int,
    vehicleRepository: VehicleLocalRepository,
    private val vehicleDeletionRepository: VehicleDeletionRepository,
    private val expenseRepository: ExpenseLocalRepository,
    private val noveltyRepository: NoveltyLocalRepository,
    private val vehicleDocumentRepository: VehicleDocumentLocalRepository
) : ViewModel() {
    val vehicle: StateFlow<Vehicle?> = vehicleRepository.getVehicleById(vehicleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val expenses: StateFlow<List<Expense>> = expenseRepository.getExpensesByVehicle(vehicleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val novelties: StateFlow<List<Novelty>> = noveltyRepository.getNoveltiesByVehicle(vehicleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val documents: StateFlow<List<VehicleDocument>> = vehicleDocumentRepository.getDocumentsByVehicle(vehicleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun deleteVehicle(
        vehicle: Vehicle,
        onDeleted: () -> Unit
    ) {
        viewModelScope.launch {
            vehicleDeletionRepository.deleteVehicleWithRelatedData(vehicle)
            onDeleted()
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }

    fun deleteNovelty(novelty: Novelty) {
        viewModelScope.launch {
            noveltyRepository.deleteNovelty(novelty)
        }
    }

    fun deleteDocument(document: VehicleDocument) {
        viewModelScope.launch {
            vehicleDocumentRepository.deleteDocument(document)
        }
    }

    class Factory(
        private val vehicleId: Int,
        private val vehicleRepository: VehicleLocalRepository,
        private val vehicleDeletionRepository: VehicleDeletionRepository,
        private val expenseRepository: ExpenseLocalRepository,
        private val noveltyRepository: NoveltyLocalRepository,
        private val vehicleDocumentRepository: VehicleDocumentLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleDetailViewModel::class.java)) {
                return VehicleDetailViewModel(
                    vehicleId = vehicleId,
                    vehicleRepository = vehicleRepository,
                    vehicleDeletionRepository = vehicleDeletionRepository,
                    expenseRepository = expenseRepository,
                    noveltyRepository = noveltyRepository,
                    vehicleDocumentRepository = vehicleDocumentRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
