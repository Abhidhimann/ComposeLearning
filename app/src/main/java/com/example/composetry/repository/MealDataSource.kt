package com.example.composetry.repository

import android.util.Log
import com.example.composetry.api.MealApiInterface
import com.example.composetry.model.MealCategoriesResponse
import com.example.composetry.utils.getClassTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MealDataSource(private val apiService: MealApiInterface) {
    private val retryCount = 3
    private val retryLimitMilies = 2000L

    // can make a generic function also like this
    suspend fun getMealCategories() : MealCategoriesResponse =
        withContext(Dispatchers.IO) {
            var currentRetry = 0
            while (currentRetry < retryCount) {
                try {
                    val response = apiService.getMealCategories()
                    if (response.isSuccessful && response.body() != null) {
                        Log.d(getClassTag(), "Response successful : $response")
                        Log.d(getClassTag(), "Response successful : ${response.body()}")
                        return@withContext response.body()!!
                    } else {
                        Log.d(getClassTag(), "Response unsuccessful: $response")
                    }
                } catch (e: Exception) {
                    Log.d(getClassTag(), "Error in network request: $e")
                }

                currentRetry++
                delay(retryLimitMilies)
            }

            // Could chage to custom exception
            throw Exception("movie api request failed after $retryCount attempts")
        }
}