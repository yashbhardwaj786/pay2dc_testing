package com.arpit.pay2dc.data.repository

import com.arpit.pay2dc.api.ApiHelper
import com.arpit.pay2dc.data.ResultData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.domain.IMainRepository
import com.arpit.pay2dc.exceptions.NoInternetException
import com.arpit.pay2dc.exceptions.TimeoutException
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) : BaseRepository(), IMainRepository {
    override suspend fun fetchData(): Result<ResultData>? {
        return try {
            val response = safeApiCall(
                call = {
                    apiHelper.getCurrenciesData()
                },
                errorMessage = "Failed to fetch Data"
            )

            response?.let {
                Result.Success(it)
            }
        } catch (exception: TimeoutException) {
            Result.Error(exception)
        } catch (exception: NoInternetException) {
            Result.Error(exception)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}