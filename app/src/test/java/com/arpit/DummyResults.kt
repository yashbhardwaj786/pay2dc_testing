package com.arpit

import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.data.ResultData

fun getCurrencyData(): ResultData {
    return ResultData(
        AED = "United Arab Emirates Dirham",
        AFN = "Afghan Afghani",
        USD = "United States Dollar",
        INR = "Indian Rupee"
    )
}

private fun getCurrency(name: String, amount: Float = 0F): Currency {
    return Currency(name, amount)
}

fun getCurrencyList(): List<Currency> {
    return listOf(getCurrency("AED"), getCurrency("AFN"), getCurrency("USD"), getCurrency("INR"))
}

fun getCurrencyListWithConversionValue(): List<Currency> {
    return listOf(getCurrency("AED", 3.67F), getCurrency("AFN", 88.9F), getCurrency("USD", 1F), getCurrency("INR", 82.07F))
}

fun getCurrencyConversionData(): CurrencyConversionData {
    return CurrencyConversionData(
        disclaimer = "Usage subject to terms",
        license = "Usage subject to terms",
        base = "USD",
        timestamp = 1710265186249,
        rates = getCurrencyConversionRates()
    )
}

private fun getCurrencyConversionRates(): ResultData {
    return ResultData(
        AED = "3.67",
        AFN = "88.9",
        USD = "1",
        INR = "82.07"
    )
}