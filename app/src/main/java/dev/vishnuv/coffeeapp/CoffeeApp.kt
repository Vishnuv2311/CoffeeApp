@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.vishnuv.coffeeapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.vishnuv.coffeeapp.components.CheckoutScreen
import dev.vishnuv.coffeeapp.components.CoffeeDetailsScreen
import dev.vishnuv.coffeeapp.components.CoffeeList
import dev.vishnuv.coffeeapp.components.IntroScreen
import dev.vishnuv.coffeeapp.components.SweetTreats
import dev.vishnuv.coffeeapp.models.TreatItem
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.models.mockTreatItems

@Composable
fun CoffeeApp() {
    val navController = rememberNavController()
    CoffeeNavHost(
        navController = navController
    )
}

@Composable
fun CoffeeNavHost(navController: NavHostController) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Screen.Intro.route) {

            composable(route = Screen.Intro.route) {
                IntroScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onClick = { navController.navigate(Screen.Home.route) })
            }

            composable(
                route = Screen.Home.route,
                arguments = Screen.Home.navArguments
            ) {
                CoffeeList(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onCoffeeClick = {
                        navController.navigate(Screen.CoffeeDetails.createRoute(it))
                    })
            }
            composable(
                route = Screen.SweetTreats.route,
                arguments = Screen.SweetTreats.navArguments
            ) {
                SweetTreats(
                    coffee = mockCoffeeItems[it.arguments?.getInt("coffeeId") ?: 0],
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onCoffeeTreatClick = { coffeeId, treatId ->
                        navController.navigate(Screen.Checkout.createRoute(coffeeId, treatId))
                    }
                )
            }

            composable(
                route = Screen.CoffeeDetails.route,
                arguments = Screen.CoffeeDetails.navArguments
            ) {
                val coffee = mockCoffeeItems[it.arguments?.getInt("coffeeId") ?: 0]
                CoffeeDetailsScreen(coffee = coffee, onSweetTreatsClick = { coffeeId ->
                    navController.navigate(Screen.SweetTreats.createRoute(coffeeId))
                })
            }

            composable(
                route = Screen.Checkout.route,
                arguments = Screen.Checkout.navArguments
            ) {
                val coffee = mockCoffeeItems[it.arguments?.getInt("coffeeId") ?: 0]
                val treatId = it.arguments?.getString("treatId")?.toIntOrNull()
                var treat: TreatItem? = null
                if (treatId != null) {
                    treat = mockTreatItems[treatId]
                }
                CheckoutScreen(
                    coffee = coffee,
                    treat = treat,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                )
            }
        }
    }
}