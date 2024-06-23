package com.arpit.pay2dc.ui.home

import com.arpit.getCurrencyConversionData
import com.arpit.getCurrencyList
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.domain.FetchCurrencyConversionUseCase
import com.arpit.pay2dc.domain.FetchCurrencyDataUseCase
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val fetchCurrencyDataUseCase: FetchCurrencyDataUseCase = mock()
    private val fetchCurrencyConversionUseCase: FetchCurrencyConversionUseCase = mock()
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private lateinit var mainViewModel: MainViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestCoroutineScope(testDispatcher)

    private val map = hashMapOf(
        "AED" to 3.67F,
        "AFN" to 88.9F,
        "USD" to 1F,
        "INR" to 82.07F,
        "VEF" to 3615460F,
    )

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(fetchCurrencyDataUseCase, fetchCurrencyConversionUseCase, moshi)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check the result for fetch currency network request`() = runTest {
        whenever(fetchCurrencyDataUseCase.fetchData()).thenReturn(Result.Success(
            getCurrencyList()
        ))
        mainViewModel = MainViewModel(fetchCurrencyDataUseCase, fetchCurrencyConversionUseCase, moshi)

        var result: Result<List<Currency?>>? = null
        testScope.runBlockingTest {
            val job = this.launch {
                mainViewModel.state.collect {
                    result = it
                }
            }
            mainViewModel.fetchCurrencies()
            job.cancel()
        }

        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getCurrencyList())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check the result for failed network request of fetch currency`() = runTest {
        whenever(fetchCurrencyDataUseCase.fetchData()).thenReturn(Result.Error(
            Exception("Something went wrong try again later")
        ))
        mainViewModel = MainViewModel(fetchCurrencyDataUseCase, fetchCurrencyConversionUseCase, moshi)

        var result: Result<List<Currency?>>? = null
        testScope.runBlockingTest {
            val job = this.launch {
                mainViewModel.state.collect {
                    result = it
                }
            }
            mainViewModel.fetchCurrencies()
            job.cancel()
        }

        Assert.assertEquals(false, result is Result.Success)
        Assert.assertEquals((result as Result.Error).exception.message, "Something went wrong try again later")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check the result for fetch currency conversion network request`() = runTest {
        whenever(fetchCurrencyConversionUseCase.fetchData()).thenReturn(Result.Success(
            getCurrencyConversionData()
        ))
        mainViewModel = MainViewModel(fetchCurrencyDataUseCase, fetchCurrencyConversionUseCase, moshi)

        var result: Result<CurrencyConversionData>? = null
        testScope.runBlockingTest {
            val job = this.launch {
                mainViewModel.stateCurrenciesConversion.collect {
                    result = it
                }
            }
            mainViewModel.fetchCurrentConversion()
            job.cancel()
        }

        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getCurrencyConversionData())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check the result for fetch currency conversion failed network request`() = runTest {
        whenever(fetchCurrencyConversionUseCase.fetchData()).thenReturn(Result.Error(
            Exception("Something went wrong try again later")
        ))
        mainViewModel = MainViewModel(fetchCurrencyDataUseCase, fetchCurrencyConversionUseCase, moshi)

        var result: Result<CurrencyConversionData>? = null
        testScope.runBlockingTest {
            val job = this.launch {
                mainViewModel.stateCurrenciesConversion.collect {
                    result = it
                }
            }
            mainViewModel.fetchCurrentConversion()
            job.cancel()
        }

        Assert.assertEquals(false, result is Result.Success)
        Assert.assertEquals((result as Result.Error).exception.message, "Something went wrong try again later")
    }

    @Test
    fun `check the amount conversion method for entered amount`() {
        val mainVM = spy(mainViewModel)
        doReturn(map).`when`(mainVM).getMap()
//        whenever(mainVM.getMap()).thenReturn(map)
        val resInr = mainVM.getAmount(82.07F, "INR")
        val resUsd = mainVM.getAmount(100F, "USD")
        Assert.assertEquals(resUsd, 100F)
        Assert.assertEquals(resInr, 1F)
    }

    @Test
    fun `check the iterate on json method to verify if the map is getting generated correctly`() = runTest {
        val res = mainViewModel.iterateOnJson(getCurrencyConversionData().rates, moshi)
        Assert.assertEquals(res.size, map.size)
        Assert.assertTrue(res.containsKey("VEF"))
        Assert.assertEquals(res["VEF"], map["VEF"])
    }
}