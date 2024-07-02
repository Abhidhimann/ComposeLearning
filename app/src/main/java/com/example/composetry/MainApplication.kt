package com.example.composetry

import android.app.Application
import com.example.composetry.api.ApiClient
import com.example.composetry.repository.MealDataSource
import com.example.composetry.repository.MealRepository

class MainApplication: Application() {

    val mealsRepository: MealRepository = MealRepository(MealDataSource(ApiClient.mealsApi()))
}