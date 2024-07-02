package com.example.composetry.repository

import android.util.Log
import com.example.composetry.model.MealCategoriesResponse
import com.example.composetry.model.MealCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.composetry.utils.Result
import com.example.composetry.utils.tempTag

class MealRepository(private val dataSource: MealDataSource) {

    private var cachedMealCategory = mutableListOf<MealCategory>()

    suspend fun getMealCategories(): Result<MealCategoriesResponse> {
        return withContext(Dispatchers.IO){
            try {
                Log.i(tempTag(),"Heello 2")
                val response = dataSource.getMealCategories()
                cachedMealCategory.clear()
                cachedMealCategory.addAll(response.categories)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    fun getMealCategoriesDetails(mealCategoryId: String): MealCategory? {
       return cachedMealCategory.firstOrNull { mealCategory -> mealCategory.id == mealCategoryId  }
    }
}