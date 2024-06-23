package com.arpit.pay2dc.domain

import com.arpit.pay2dc.data.ResultData
import com.arpit.pay2dc.data.model.Result

interface IMainRepository {
    suspend fun fetchData(): Result<ResultData>?
}