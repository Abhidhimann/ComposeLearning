package com.example.composetry.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.composetry.MainApplication
import com.example.composetry.model.MealCategory
import com.example.composetry.repository.MealRepository
import kotlinx.coroutines.launch
import com.example.composetry.utils.Result
import com.example.composetry.utils.tempTag

class MealCategoryViewModel(private val repository: MealRepository) : ViewModel() {

//    private val _mealCategoriesState: MutableStateFlow<List<MealCategories>> =
//        MutableStateFlow(emptyList())
//    val mealCategoriesState: StateFlow<List<MealCategories>> = _mealCategoriesState

    private val _mealCategoryState = mutableStateOf<List<MealCategory>>(emptyList())
    val mealCategoryState: State<List<MealCategory>> = _mealCategoryState

    init {
        getMealCategories()
    }

    private fun getMealCategories() = viewModelScope.launch {
        // show someLoading
        Log.i(tempTag(), "Hello")
        when (val result = repository.getMealCategories()) {
            is Result.Success -> {
                Log.i(tempTag(), "Heello 3 ${result.data!!.categories[1].name}")
//                _mealCategoriesState.emit(result.data!!.categories)
                _mealCategoryState.value = result.data.categories
            }
            is Result.Error -> {
                //show some error page
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras

                return MealCategoryViewModel(
                    (application as MainApplication).mealsRepository
                ) as T
            }
        }
    }
}