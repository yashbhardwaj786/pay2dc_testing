package com.arpit.pay2dc.domain

import com.arpit.getCurrencyConversionData
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.model.Result

class FakeFetchCurrencyConversionRepo : IFetchCurrencyConversionRepository {
    override suspend fun fetchData(): Result<CurrencyConversionData> {
        return Result.Success(getCurrencyConversionData())
//        return Result.Error(Exception("Something went wrong try again later"))
    }
}