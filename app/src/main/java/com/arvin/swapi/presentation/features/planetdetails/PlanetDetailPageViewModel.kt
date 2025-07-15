package com.arvin.swapi.presentation.features.planetdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.usecase.PlanetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlanetDetailPageUIState(
    var isLoading: Boolean = true,
    var planet: Planet? = null,
    val networkError: Boolean = false
)

class PlanetDetailsPageViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetUseCase: PlanetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanetDetailPageUIState())
    val uiState: StateFlow<PlanetDetailPageUIState> = _uiState

    private val planetId: Int = (savedStateHandle["planetId"] ?: "0").toInt()

    init {
        loadPlanetDetail()
    }

    fun loadPlanetDetail() {
        viewModelScope.launch {
            when (val result = planetUseCase.getPlanetById(planetId)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(planet = result.data, isLoading = false, networkError = false)
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(networkError = true, isLoading = false)
                    }
                }
            }
        }
    }
}