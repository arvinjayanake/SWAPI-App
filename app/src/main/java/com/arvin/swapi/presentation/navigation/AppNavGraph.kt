package com.arvin.swapi.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arvin.swapi.domain.model.getIdFromUrl
import com.arvin.swapi.presentation.features.planetdetails.PlanetDetailsPage
import com.arvin.swapi.presentation.features.planetlist.PlanetListPage

/**
 * Defines navigation destination route names for the app.
 *
 * Contains constants for identifying the planet list and planet details pages in the navigation graph.
 */
object Destinations {

    /** Route name for the planet list page. */
    const val PLANET_LIST = "planet_list"

    /** Route name for the planet details page. */
    const val PLANET_DETAILS = "planet_details"
}

/**
 * App navigation graph, defining navigation structure and destinations using Jetpack Compose Navigation.
 *
 * Sets up the routes for the planet list and planet details pages.
 *
 * @param navigationController The [NavHostController] managing navigation actions.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavGraph(navigationController: NavHostController) {
    NavHost(navController = navigationController, startDestination = Destinations.PLANET_LIST) {
        composable(Destinations.PLANET_LIST) {
            PlanetListPage { planet ->
                navigationController.navigate("${Destinations.PLANET_DETAILS}/${planet.getIdFromUrl()}")
            }
        }
        composable(route = "${Destinations.PLANET_DETAILS}/{planetId}") {
            PlanetDetailsPage {
                navigationController.popBackStack()
            }
        }
    }

}