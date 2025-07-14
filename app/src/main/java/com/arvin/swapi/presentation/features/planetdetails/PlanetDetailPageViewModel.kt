package com.arvin.swapi.presentation.features.planetdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.usecase.PlanetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlanetDetailPageUIState(
    var isLoading: Boolean = false,
    var planet: Planet? = null,
)

class PlanetDetailsPageViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetUseCase: PlanetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanetDetailPageUIState())
    val uiState: StateFlow<PlanetDetailPageUIState> = _uiState

    private val planetId: Int = (savedStateHandle["planetId"] ?: "0").toInt()

    init {
        loadPlanets()
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            val planet = planetUseCase.getPlanet(planetId)
            _uiState.update {
                it.copy(planet = planet)
            }
        }
    }

}