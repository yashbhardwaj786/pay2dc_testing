package com.arpit.pay2dc.domain

import com.arpit.getCurrencyConversionData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.data.repository.UserPreferencesRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchCurrencyConversionUseCaseTest {

    private val fetchCurrencyConversionRepository: FakeFetchCurrencyConversionRepo = mock()
    private val userPreferencesRepository: UserPreferencesRepository = mock()

    private lateinit var fetchCurrencyConversionUseCase: FetchCurrencyConversionUseCase

    @Before
    fun setUp() {
        fetchCurrencyConversionUseCase = FetchCurrencyConversionUseCase(fetchCurrencyConversionRepository, userPreferencesRepository)
    }

    @Test
    fun `check result when fetch currencies conversion api is called`() = runTest {
        whenever(fetchCurrencyConversionRepository.fetchData()).thenReturn(Result.Success(
            getCurrencyConversionData()
        ))

        val result = fetchCurrencyConversionUseCase.fetchData()
        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getCurrencyConversionData())
    }

    @Test
    fun `check data received when fetch currencies conversion api is called`() = runTest {
        whenever(fetchCurrencyConversionRepository.fetchData()).thenReturn(Result.Success(
            getCurrencyConversionData()
        ))

        val result = fetchCurrencyConversionUseCase.fetchData()
        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data.base, "USD")
    }
}