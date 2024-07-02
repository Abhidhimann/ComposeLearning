package com.example.composetry.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetry.ui.theme.ComposeTryTheme
import com.example.composetry.utils.ScreenParam
import com.example.composetry.utils.Screens
import com.example.composetry.viewModel.MealCategoryDetailsViewModel
import com.example.composetry.viewModel.MealCategoryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mealCategoryViewModel: MealCategoryViewModel by viewModels { MealCategoryViewModel.Factory }
        val mealCategoryDetailsViewModel: MealCategoryDetailsViewModel by viewModels { MealCategoryDetailsViewModel.Factory}
        setContent {
            LazyColumn(content = )
            ComposeTryTheme {
                MealsApp(mealCategoryViewModel = mealCategoryViewModel, mealCategoryDetailsViewModel =  mealCategoryDetailsViewModel)
            }
        }
    }
}


@Composable
fun MealsApp(mealCategoryViewModel: MealCategoryViewModel, mealCategoryDetailsViewModel: MealCategoryDetailsViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MEAL_CATEGORY.value) {
        composable(route = Screens.MEAL_CATEGORY.value) {
            MealCategoriesScreen(viewModel = mealCategoryViewModel) { mealCategoryId ->
                navController.navigate(route = Screens.MEAL_CATEGORY_DETAILS.value+"/$mealCategoryId")
            }
        }
        composable(
            route = Screens.MEAL_CATEGORY_DETAILS.value + "/{${ScreenParam.MEAL_CATEGORY_ID.value}}",
            arguments = listOf(navArgument(ScreenParam.MEAL_CATEGORY_ID.value) { type = NavType.StringType })
        ) { navBackStackEntry: NavBackStackEntry ->
            MealCategoryDetailsScreen(navBackStackEntry.arguments!!.getString(ScreenParam.MEAL_CATEGORY_ID.value)!!,
//                mealCategoryDetailsViewModel
            )
        }
    }
}
