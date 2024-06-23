package com.arpit.pay2dc.api

import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.ResultData
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("currencies.json")
    suspend fun getCurrenciesData(): Response<ResultData>

    @GET("historical/{date}.json")
    suspend fun getCurrencyConversionData(@Path("date") date: String): Response<CurrencyConversionData>
}