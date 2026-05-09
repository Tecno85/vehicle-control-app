package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDocumentLocalRepository
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class VehicleListViewModel(
    vehicleRepository: VehicleLocalRepository,
    vehicleDocumentRepository: VehicleDocumentLocalRepository
) : ViewModel() {
    val vehicles: StateFlow<List<Vehicle>> = vehicleRepository.getAllVehicles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val documents: StateFlow<List<VehicleDocument>> = vehicleDocumentRepository.getAllDocumentsByDueDate()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    class Factory(
        private val vehicleRepository: VehicleLocalRepository,
        private val vehicleDocumentRepository: VehicleDocumentLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
                return VehicleListViewModel(
                    vehicleRepository = vehicleRepository,
                    vehicleDocumentRepository = vehicleDocumentRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
