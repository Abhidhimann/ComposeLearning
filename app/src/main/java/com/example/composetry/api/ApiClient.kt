package com.example.composetry.api

import com.example.composetry.utils.ApiConstants
import com.example.composetry.utils.RetroFitClientHelper

object ApiClient {

    fun mealsApi(): MealApiInterface =
        RetroFitClientHelper().getApiClient(ApiConstants.BASE_URL.getValue())
            .create(MealApiInterface::class.java)
}