package com.ivanmadrid.vehiclecontrolapp.presentation.screens.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.VehicleDocumentLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.VehicleDocument
import kotlinx.coroutines.launch

class DocumentFormViewModel(
    private val vehicleDocumentRepository: VehicleDocumentLocalRepository
) : ViewModel() {
    fun saveDocument(
        document: VehicleDocument,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            if (document.id == 0) {
                vehicleDocumentRepository.insertDocument(document)
            } else {
                vehicleDocumentRepository.updateDocument(document)
            }

            onSaved()
        }
    }

    class Factory(
        private val vehicleDocumentRepository: VehicleDocumentLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DocumentFormViewModel::class.java)) {
                return DocumentFormViewModel(
                    vehicleDocumentRepository = vehicleDocumentRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
