package com.arpit.pay2dc.data.repository

import com.arpit.pay2dc.api.ApiHelper
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.domain.IFetchCurrencyConversionRepository
import com.arpit.pay2dc.exceptions.NoInternetException
import com.arpit.pay2dc.exceptions.TimeoutException
import com.arpit.pay2dc.extension.getCurrentDate
import javax.inject.Inject

open class FetchCurrencyConversionRepository @Inject constructor(private val apiHelper: ApiHelper): BaseRepository(),
    IFetchCurrencyConversionRepository {
    override suspend fun fetchData(): Result<CurrencyConversionData>? {
        return try {
            val response = safeApiCall(
                call = {
                    apiHelper.getCurrencyConversionData(getCurrentDate())
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