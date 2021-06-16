package com.example.currencyrates.retrofit

object Common {
    var BASE_URL = "https://cbu.uz/uz/arkhiv-kursov-valyut/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getRetrofit(BASE_URL).create(RetrofitService::class.java)
}