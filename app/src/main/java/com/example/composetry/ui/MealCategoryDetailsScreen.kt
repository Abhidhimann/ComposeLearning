package com.example.composetry.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetry.viewModel.MealCategoryDetailsViewModel

@Composable
fun MealCategoryDetailsScreen(mealCategoryId: String,
                              mealCategoryDetailsViewModel: MealCategoryDetailsViewModel
){
//    val mealCategoryDetailsViewModel: MealCategoryDetailsViewModel = viewModel()
    mealCategoryDetailsViewModel.getMealCategoryDetails(mealCategoryId)
    val mealCategoryDetails = mealCategoryDetailsViewModel.mealCategoryDetails.value
    if(mealCategoryDetails==null){
        // show loading screen
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "Loading", textAlign = TextAlign.Center)
        }
    }
    else {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Row {
                    CardImageMeal(imageUrl = "", modifier = Modifier.size(100.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MealDetailsScreenPreview(){
    MealCategoryDetailsScreen("",
        viewModel()
    )
}