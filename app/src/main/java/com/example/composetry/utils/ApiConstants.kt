package com.example.composetry.utils

enum class ApiConstants(private val value: String) {
    BASE_URL("https://www.themealdb.com/api/json/v1/1/"),
    TIME_OUT("2");

    fun getValue(): String {
        return value
    }
}

fun Any.getClassTag(): String = this::class.java.simpleName

fun Any.tempTag(): String = "TEMPTAG"