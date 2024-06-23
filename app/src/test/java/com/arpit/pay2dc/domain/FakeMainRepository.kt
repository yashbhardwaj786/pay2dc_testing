package com.arpit.pay2dc.domain

import com.arpit.getCurrencyData
import com.arpit.pay2dc.data.ResultData
import com.arpit.pay2dc.data.model.Result

class FakeMainRepository : IMainRepository {
    override suspend fun fetchData(): Result<ResultData> {
        return Result.Success(getCurrencyData())
    }
}