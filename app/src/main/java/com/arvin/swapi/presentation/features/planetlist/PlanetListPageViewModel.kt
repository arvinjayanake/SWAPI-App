package com.arvin.swapi.presentation.features.planetlist

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
 * UI state for the Planet List Page.
 *
 * Holds the loading status, planet list, current page, pagination state, and network error flag.
 *
 * @property isLoading True if the list is currently loading.
 * @property planets The list of loaded [Planet] objects.
 * @property currentPage The current page number being displayed.
 * @property isEndReached True if all available data has been loaded (no more pages).
 * @property networkError True if a network error occurred during data loading.
 */
data class PlanetListPageUIState(
    var isLoading: Boolean = true,
    var planets: List<Planet> = emptyList(),
    val currentPage: Int = 1,
    val isEndReached: Boolean = false,
    val networkError: Boolean = false
)

/**
 * ViewModel for the Planet List Page.
 *
 * Handles loading and pagination of the planet list, manages UI state, and exposes retry and next-page logic.
 *
 * @property planetUseCase Use case for fetching planet data.
 */
class PlanetListPageViewModel(private val planetUseCase: PlanetUseCase) : ViewModel() {

    /** Backing state flow for the UI state. */
    private val _uiState = MutableStateFlow(PlanetListPageUIState())

    /** Exposed state flow for observing UI state changes. */
    val uiState: StateFlow<PlanetListPageUIState> = _uiState

    /** Tracks if a load operation is currently in progress. */
    private var isLoadingMore = false

    init {
        loadPlanets(1)
    }

    /**
     * Loads planets for the given page, updating UI state and handling pagination logic.
     *
     * @param page The page number to load.
     */
    private fun loadPlanets(page: Int) {
        if (isLoadingMore || _uiState.value.isEndReached) return

        isLoadingMore = true
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = planetUseCase.getPlanets(page)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        isLoadingMore = false

                        val allPlanets = it.planets + result.data
                        it.copy(
                            isLoading = false,
                            planets = allPlanets,
                            currentPage = page,
                            isEndReached = result.data.isEmpty(),
                            networkError = false
                        )
                    }
                }

                is ApiResult.Error -> {
                    isLoadingMore = false
                    _uiState.update {
                        it.copy(
                            isEndReached = (result.statusCode == 404),
                            isLoading = false,
                            networkError = true
                        )
                    }

                    print("API ERROR: ${result.message}")
                }
            }
        }
    }

    /**
     * Retries loading planets for the specified page, typically used after a failure.
     *
     * @param page The page number to retry loading.
     */
    fun retry(page: Int){
        loadPlanets(page)
    }

    /**
     * Loads the next page of planets, incrementing the current page.
     */
    fun loadNextPage() {
        loadPlanets(_uiState.value.currentPage + 1)
    }

}