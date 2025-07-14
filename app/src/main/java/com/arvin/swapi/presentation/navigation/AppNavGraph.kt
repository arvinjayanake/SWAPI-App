package com.arvin.swapi.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arvin.swapi.domain.model.getIdFromUrl
import com.arvin.swapi.presentation.features.planetdetails.PlanetDetailsPage
import com.arvin.swapi.presentation.features.planetlist.PlanetListPage

object Destinations {
    const val planetlist = "planetlist"
    const val planetdetails = "planetdetails"
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavGraph(navigationController: NavHostController) {
    NavHost(navController = navigationController, startDestination = Destinations.planetlist) {
        composable(Destinations.planetlist) {
            PlanetListPage { planet ->
                navigationController.navigate("${Destinations.planetdetails}/${planet.getIdFromUrl()}")
            }
        }
        composable(route = "${Destinations.planetdetails}/{planetId}") {
            PlanetDetailsPage {
                navigationController.popBackStack()
            }
        }
    }

}