package com.ivanmadrid.vehiclecontrolapp.presentation.screens.novelties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ivanmadrid.vehiclecontrolapp.data.local.repository.NoveltyLocalRepository
import com.ivanmadrid.vehiclecontrolapp.domain.model.Novelty
import kotlinx.coroutines.launch

class NoveltyFormViewModel(
    private val noveltyRepository: NoveltyLocalRepository
) : ViewModel() {
    fun saveNovelty(
        novelty: Novelty,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            noveltyRepository.insertNovelty(novelty)
            onSaved()
        }
    }

    class Factory(
        private val noveltyRepository: NoveltyLocalRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoveltyFormViewModel::class.java)) {
                return NoveltyFormViewModel(
                    noveltyRepository = noveltyRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
