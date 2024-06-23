package com.arpit.pay2dc.api

import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.ResultData
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getCurrenciesData(): Response<ResultData> {
        return apiService.getCurrenciesData()
    }

    override suspend fun getCurrencyConversionData(date: String): Response<CurrencyConversionData> {
        return apiService.getCurrencyConversionData(date)
    }

}