package com.arpit.pay2dc.domain

import com.arpit.getCurrencyList
import com.arpit.pay2dc.api.ApiHelper
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.data.repository.UserPreferencesRepository
import com.nhaarman.mockito_kotlin.mock
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FetchCurrencyDataUseCaseTest {

    private val apiHelper: ApiHelper = mock()
    private lateinit var mainRepository: FakeMainRepository
    private val userPreferencesRepository: UserPreferencesRepository = mock()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private lateinit var fetchCurrencyDataUseCase: FetchCurrencyDataUseCase

    @Before
    fun setUp() {
        mainRepository = FakeMainRepository()
        fetchCurrencyDataUseCase = FetchCurrencyDataUseCase(mainRepository, userPreferencesRepository, moshi)
    }

    @Test
    fun `check result when fetch currencies api is called`() = runTest {
//        whenever(mainRepository.fetchData()).thenReturn(
//            Result.Success(
//            getCurrencyData()
//        ))

        val result = fetchCurrencyDataUseCase.fetchData()
        assertEquals(true, result is Result.Success)
        assertEquals((result as Result.Success).data.size, getCurrencyList().size)
    }

    @Test
    fun `check data received when fetch currencies api is called`() = runTest {
        /**
         * This was getting called when the repository was mocked, but later on changed to the actual
         * object as the repository was fake repository
         */
//        whenever(mainRepository.fetchData()).thenReturn(
//            Result.Success(
//            getCurrencyData()
//        ))

        val result = fetchCurrencyDataUseCase.fetchData()
        assertEquals(true, result is Result.Success)
        Assert.assertTrue((result as Result.Success).data.contains(Currency("AED", 0.0F)))
    }
}