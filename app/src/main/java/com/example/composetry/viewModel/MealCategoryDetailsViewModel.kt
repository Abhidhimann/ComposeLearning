package com.example.composetry.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.composetry.MainApplication
import com.example.composetry.model.MealCategory
import com.example.composetry.repository.MealRepository
import com.example.composetry.utils.ScreenParam
import com.example.composetry.utils.tempTag

class MealCategoryDetailsViewModel(private val repository: MealRepository): ViewModel() {

    private val _mealCategoryDetails = mutableStateOf<MealCategory?>(null)
    val mealCategoryDetails: MutableState<MealCategory?> = _mealCategoryDetails

//    init {
//    }


    fun getMealCategoryDetails(mealCategoryId: String) {
//        val mealCategoryId = savedStateHandle.get<String>(ScreenParam.MEAL_CATEGORY_ID.value)?: ""
//          class MealCategoryDetailsViewModel(private val savedStateHandle: SavedStateHandle
//          can also use this
        _mealCategoryDetails.value = repository.getMealCategoriesDetails(mealCategoryId)
        Log.i(tempTag(), "second screen id: $mealCategoryId " + _mealCategoryDetails.value )
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras

                return MealCategoryDetailsViewModel(
                    (application as MainApplication).mealsRepository
                ) as T
            }
        }
    }
}