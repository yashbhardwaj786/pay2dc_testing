package com.arpit.pay2dc.domain

import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.model.Result

interface IFetchCurrencyConversionRepository {
    suspend fun fetchData(): Result<CurrencyConversionData>?
}