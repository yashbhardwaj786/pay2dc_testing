package com.arpit.pay2dc.data.repository

import com.arpit.getCurrencyConversionData
import com.arpit.pay2dc.api.ApiHelper
import com.arpit.pay2dc.data.model.Result
import com.arpit.pay2dc.extension.getCurrentDate
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FetchCurrencyConversionRepositoryTest {

    private val apiHelper: ApiHelper = mock()

    private lateinit var repository: FetchCurrencyConversionRepository

    @Before
    fun setUp() {
        repository = FetchCurrencyConversionRepository(apiHelper)
    }

    @Test
    fun `fetch the currency conversion rates on the basis of date and return result`() = runTest {
        whenever(apiHelper.getCurrencyConversionData(getCurrentDate())).thenReturn(
            Response.success(
                getCurrencyConversionData()
            )
        )
        val result = repository.fetchData()
        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getCurrencyConversionData())
    }

    @Test
    fun `fetch the currency conversion rates and return error`() = runTest {
        whenever(apiHelper.getCurrencyConversionData(getCurrentDate())).thenReturn(
            Response.error(
                401,
                "Unauthorized".toResponseBody()
            )
        )
        val result = repository.fetchData()
        Assert.assertEquals(true, result is Result.Error)
        Assert.assertEquals(
            "java.io.IOException: Error Occurred during getting safe Api result, Custom ERROR - Failed to fetch Data",
            (result as Result.Error).exception.message
        )
    }
}