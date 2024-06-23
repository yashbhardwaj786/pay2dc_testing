package com.arpit.pay2dc.data.repository

import com.arpit.getCurrencyData
import com.arpit.pay2dc.api.ApiHelper
import com.arpit.pay2dc.data.model.Result
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class MainRepositoryTest {

    private val apiHelper: ApiHelper = mock()

    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        repository = MainRepository(apiHelper)
    }

    @Test
    fun `fetch the currencies and return result`() = runTest {
        whenever(apiHelper.getCurrenciesData()).thenReturn(Response.success(getCurrencyData()))
        val result = repository.fetchData()
        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals((result as Result.Success).data, getCurrencyData())
        Assert.assertEquals(result.data.AED, "United Arab Emirates Dirham")
    }

    @Test
    fun `fetch the currencies and return error`() = runTest {
        whenever(apiHelper.getCurrenciesData()).thenReturn(
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