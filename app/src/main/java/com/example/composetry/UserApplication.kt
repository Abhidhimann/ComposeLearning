package com.example.composetry

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class UserApplication {
}

@Composable
fun SimpleChatApplication(users: List<UserProfile> = usersProfile) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.USER_LIST.value) {
        composable(route = Screens.USER_LIST.value) {
            UserListScreen(users, navController)
        }
        composable(
            route = Screens.USER_DETAILS.value + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {navBackStackEntry: NavBackStackEntry ->
            UserDetailsScreen(navBackStackEntry.arguments!!.getInt("userId"), navController)
        }
    }

}

enum class Screens(val value: String) {
    USER_LIST("user_list"),
    USER_DETAILS("user_details");
}