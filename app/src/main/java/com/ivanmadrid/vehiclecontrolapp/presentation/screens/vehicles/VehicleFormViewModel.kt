package com.ivanmadrid.vehiclecontrolapp.presentation.screens.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Vehicle
import kotlinx.coroutines.launch

class VehicleFormViewModel(
    private val vehicleRepository: VehicleLocalRepository
) : ViewModel() {
    fun saveVehicle(
        vehicle: Vehicle,
        onValidationError: (String) -> Unit,
        onSaved: (Vehicle) -> Unit
    ) {
        viewModelScope.launch {
            val duplicatedPlate = if (vehicle.id == 0) {
                vehicleRepository.plateExists(vehicle.plate)
            } else {
                vehicleRepository.plateExistsForOtherVehicle(
                    plate = vehicle.plate,
                    vehicleId = vehicle.id
                )
            }

            if (duplicatedPlate) {
                onValidationError("Ya existe un vehículo registrado con esa placa.")
                return@launch
            }

            if (vehicle.id == 0) {
                vehicleRepository.insertVehicle(vehicle)
            } else {
                vehicleRepository.updateVehicle(vehicle)
            }

            onSaved(vehicle)
        }
    }

    class Factory(
        private val vehicleRepository: VehicleLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleFormViewModel::class.java)) {
                return VehicleFormViewModel(
                    vehicleRepository = vehicleRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
