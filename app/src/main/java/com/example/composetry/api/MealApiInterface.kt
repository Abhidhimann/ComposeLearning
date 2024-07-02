package com.example.composetry.api

import com.example.composetry.model.MealCategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MealApiInterface {
    @GET("categories.php")
    suspend fun getMealCategories(): Response<MealCategoriesResponse>

//    suspend fun getMealCategoryDetails():
}