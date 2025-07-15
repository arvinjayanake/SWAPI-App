package com.arvin.swapi.presentation.features.planetdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arvin.swapi.domain.model.getHighResImgUrl
import com.arvin.swapi.presentation.shared.components.NoInternetScreen
import com.arvin.swapi.presentation.shared.components.PlanetImageView
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailsPage(onBackPressed: () -> Unit) {

    val viewModel: PlanetDetailsPageViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Planet",
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues),
            ) {
                if (uiState.isLoading) {
                    //Loading
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                } else {
                    if (uiState.networkError) {
                        //Network error
                        NoInternetScreen(onRetry = {
                            viewModel.loadPlanetDetail()
                        })
                    } else {
                        //Planet UI
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            PlanetImageView(
                                imgUrl = uiState.planet?.getHighResImgUrl(),
                                height = 300.dp,
                                imageCornerRadius = 0.dp
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = uiState.planet?.name ?: "-",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "This planet completes one full orbit around its star in ${uiState.planet?.orbitalPeriod ?: "-"} days and experiences a gravitational force of ${uiState.planet?.gravity ?: "-"} at its surface.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    )
}