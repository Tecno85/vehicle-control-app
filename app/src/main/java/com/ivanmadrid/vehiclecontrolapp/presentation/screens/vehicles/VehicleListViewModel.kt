package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class VehicleListViewModel(
    vehicleRepository: VehicleLocalRepository
) : ViewModel() {
    val vehicles: StateFlow<List<Vehicle>> = vehicleRepository.getAllVehicles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    class Factory(
        private val vehicleRepository: VehicleLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
                return VehicleListViewModel(vehicleRepository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
