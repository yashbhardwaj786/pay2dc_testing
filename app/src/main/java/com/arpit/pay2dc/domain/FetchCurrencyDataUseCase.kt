package com.arpit.pay2dc.domain

import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.ResultData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.data.repository.CURRENCIES_LIST
import com.arpit.pay2dc.data.repository.LAST_UPDATED
import com.arpit.pay2dc.data.repository.MainRepository
import com.arpit.pay2dc.data.repository.UserPreferencesRepository
import com.arpit.pay2dc.exceptions.NoInternetException
import com.arpit.pay2dc.extension.objectToJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class FetchCurrencyDataUseCase @Inject constructor(
    private val mainRepository: IMainRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val moshi: Moshi
) {
    suspend fun fetchData(): Result<List<Currency>> {
        if ((userPreferencesRepository.getLongValue(LAST_UPDATED) > 0 &&
            TimeUnit.MILLISECONDS.toMinutes(
                System.currentTimeMillis() - userPreferencesRepository.getLongValue(
                    LAST_UPDATED
                )
            ) <= MIN_TIME_DIFFERENCE)
        ) {
            return getLocalData(NoInternetException("Please check internet connection."))
        } else {
            return when (val result = mainRepository.fetchData() as Result) {
                is Result.Success -> {
                    Result.Success(iterateOnJsonObject(result.data))
                }

                is Result.Error -> {
                    if(result.exception is NoInternetException) {
                        return getLocalData(result.exception)
                    }
                    Result.Error(result.exception)
                }
            }
        }
    }

    private fun getLocalData(noInternetException: NoInternetException) = userPreferencesRepository.getCurrenciesData(
        CURRENCIES_LIST
    )?.let {
        Result.Success(it)
    } ?: run {
        Result.Error(noInternetException)
    }

    private fun iterateOnJsonObject(data: ResultData): List<Currency> {
        val json = moshi.objectToJson(data)
        val jsonObject = JSONObject(json)
        val keys = jsonObject.keys()

        val arr: ArrayList<Currency> = ArrayList()
        while (keys.hasNext()) {
            val currency = Currency(keys.next())
            arr.add(currency)
        }
        userPreferencesRepository.saveCurrenciesData(CURRENCIES_LIST, arr)
        return arr
    }

//    fun getJsonAdapter(): JsonAdapter<ResultData> {
//        val type = Types.newParameterizedType(
//            ResultData::class.java,
//            ResultData::class.java
//        )
//        return moshi.adapter(type)
//    }
}