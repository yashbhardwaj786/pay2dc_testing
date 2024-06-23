package com.arpit.pay2dc.api

import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.ResultData
import retrofit2.Response

interface ApiHelper {
    suspend fun getCurrenciesData(): Response<ResultData>

    suspend fun getCurrencyConversionData(date: String): Response<CurrencyConversionData>
}