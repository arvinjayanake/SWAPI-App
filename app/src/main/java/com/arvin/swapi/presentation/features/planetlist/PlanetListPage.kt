package com.arvin.swapi.presentation.features.planetlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.presentation.shared.components.PlanetCardView
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListPage(onPlanetClicked: (Planet) -> Unit) {

    val viewModel: PlanetListPageViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "StarWars Planets",
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(uiState.planets) { planet ->
                        PlanetCardView(planet = planet, onClick = { onPlanetClicked(planet) })
                    }
                }
            }
        }
    )
}