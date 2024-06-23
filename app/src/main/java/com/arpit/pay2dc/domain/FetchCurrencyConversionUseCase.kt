package com.arpit.pay2dc.domain

import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.data.repository.CURRENCIES_CONVERSION_LIST
import com.arpit.pay2dc.data.repository.FetchCurrencyConversionRepository
import com.arpit.pay2dc.data.repository.LAST_UPDATED
import com.arpit.pay2dc.data.repository.UserPreferencesRepository
import com.arpit.pay2dc.exceptions.NoInternetException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val MIN_TIME_DIFFERENCE = 30

class FetchCurrencyConversionUseCase @Inject constructor(
    private val fetchCurrencyConversionRepository: IFetchCurrencyConversionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun fetchData(): Result<CurrencyConversionData> {
        if ((userPreferencesRepository.getLongValue(LAST_UPDATED) > 0 &&
            TimeUnit.MILLISECONDS.toMinutes(
                System.currentTimeMillis() - userPreferencesRepository.getLongValue(
                    LAST_UPDATED
                )
            ) <= MIN_TIME_DIFFERENCE)
        ) {
            return userPreferencesRepository.getCurrenciesConversionData<CurrencyConversionData>(
                CURRENCIES_CONVERSION_LIST
            )?.let {
                Result.Success(it)
            } ?: run {
                Result.Error(Exception())
            }
        } else {
            return when (val result = fetchCurrencyConversionRepository.fetchData() as Result) {
                is Result.Success -> {
                    userPreferencesRepository.saveCurrenciesConversionData(
                        CURRENCIES_CONVERSION_LIST,
                        result.data
                    )
                    userPreferencesRepository.saveLongValue(
                        LAST_UPDATED,
                        System.currentTimeMillis()
                    )
                    Result.Success(result.data)
                }

                is Result.Error -> {
                    if(result.exception is NoInternetException) {
                        return userPreferencesRepository.getCurrenciesConversionData<CurrencyConversionData>(
                            CURRENCIES_CONVERSION_LIST
                        )?.let {
                            Result.Success(it)
                        } ?: run {
                            Result.Error(Exception())
                        }
                    }
                    Result.Error(result.exception)
                }
            }
        }
    }
}