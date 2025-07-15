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

/**
 * UI state for the Planet Details Page.
 *
 * Holds loading status, planet data, and network error state for the detail view.
 *
 * @property isLoading True if the detail is currently loading.
 * @property planet The [Planet] data to display, or null if not loaded.
 * @property networkError True if a network error occurred.
 */
data class PlanetDetailPageUIState(
    var isLoading: Boolean = true,
    var planet: Planet? = null,
    val networkError: Boolean = false
)

/**
 * ViewModel for the Planet Details Page.
 *
 * Handles the logic for loading and exposing planet details based on the planet ID from navigation arguments.
 * Manages UI state via [PlanetDetailPageUIState].
 *
 * @property savedStateHandle State handle for retrieving the planet ID from navigation.
 * @property planetUseCase Use case for planet-related operations.
 */
class PlanetDetailsPageViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetUseCase: PlanetUseCase
) : ViewModel() {

    /** Backing state flow for the UI state. */
    private val _uiState = MutableStateFlow(PlanetDetailPageUIState())

    /** Exposed state flow for observing UI state changes. */
    val uiState: StateFlow<PlanetDetailPageUIState> = _uiState

    /** The ID of the planet to display, retrieved from navigation arguments. */
    private val planetId: Int = (savedStateHandle["planetId"] ?: "0").toInt()

    init {
        loadPlanetDetail()
    }

    /**
     * Loads the details for the planet with [planetId], updating UI state based on the result.
     */
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