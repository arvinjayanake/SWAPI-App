package com.arvin.swapi.presentation.features.planetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.usecase.PlanetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlanetListPageUIState(
    var isLoading: Boolean = true,
    var planets: List<Planet> = emptyList()
)

class PlanetListPageViewModel(private val planetUseCase: PlanetUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanetListPageUIState())
    val uiState: StateFlow<PlanetListPageUIState> = _uiState

    init {
        loadPlanets()
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            val planets = planetUseCase.getPlanets(page = 1)
            _uiState.update {
                it.copy(planets = planets)
            }
        }
    }

}