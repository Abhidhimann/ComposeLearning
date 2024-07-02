package com.example.composetry.model

import com.google.gson.annotations.SerializedName

data class MealCategoriesResponse(
    @SerializedName("categories")
    val categories: List<MealCategory>
)

data class MealCategory(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryDescription")
    val description: String,
    @SerializedName("strCategoryThumb")
    val imageUrl: String,
)
