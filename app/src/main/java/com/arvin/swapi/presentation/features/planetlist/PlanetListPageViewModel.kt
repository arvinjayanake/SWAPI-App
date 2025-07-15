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

data class PlanetListPageUIState(
    var isLoading: Boolean = true,
    var planets: List<Planet> = emptyList(),
    val currentPage: Int = 1,
    val isEndReached: Boolean = false,
    val networkError: Boolean = false
)

class PlanetListPageViewModel(private val planetUseCase: PlanetUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanetListPageUIState())
    val uiState: StateFlow<PlanetListPageUIState> = _uiState

    private var isLoadingMore = false

    init {
        loadPlanets(1)
    }

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

    fun retry(page: Int){
        loadPlanets(page)
    }

    fun loadNextPage() {
        loadPlanets(_uiState.value.currentPage + 1)
    }

}