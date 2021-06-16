package com.example.currencyrates.retrofit

import com.example.currencyrates.models.CurrencyRate
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("json")
    fun getCurrencyRateList(): Call<List<CurrencyRate>>
}