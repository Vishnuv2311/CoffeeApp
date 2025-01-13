package dev.vishnuv.coffeeapp

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Intro : Screen("intro")

    data object Home : Screen("home")

    data object CoffeeDetails : Screen(
        route = "coffeeDetails/{coffeeId}",
        navArguments = listOf(navArgument("coffeeId") {
            type = NavType.IntType
        })
    ) {
        fun createRoute(coffeeId: Int) = "coffeeDetails/${coffeeId}"
    }

    data object SweetTreats : Screen(
        route = "sweetTreats/{coffeeId}",
        navArguments = listOf(navArgument("coffeeId") {
            type = NavType.IntType
        })
    ) {
        fun createRoute(coffeeId: Int) = "sweetTreats/${coffeeId}"
    }

    data object Checkout : Screen(
        route = "checkout/{coffeeId}/{treatId}",
        navArguments = listOf(
            navArgument("coffeeId") {
                type = NavType.IntType
            },
            navArgument("treatId") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        fun createRoute(coffeeId: Int, treatId: Int?) = "checkout/${coffeeId}/${treatId ?:""}"
    }

}