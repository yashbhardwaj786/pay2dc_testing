package com.arpit.pay2dc.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyConversionData(
    @field:Json(name = "disclaimer") val disclaimer: String,
    @field:Json(name = "license") val license: String,
    @field:Json(name = "timestamp") val timestamp: Long,
    @field:Json(name = "base") val base: String,
    @field:Json(name = "rates") val rates: ResultData
)