package com.arpit.pay2dc.data.repository

import android.content.SharedPreferences
import com.arpit.pay2dc.data.Currency
import com.arpit.pay2dc.data.CurrencyConversionData
import com.arpit.pay2dc.extension.jsonToObject
import com.arpit.pay2dc.extension.objectToJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

const val CURRENCIES_LIST = "currencies_list"
const val LAST_UPDATED = "last_updated"
const val CURRENCIES_CONVERSION_LIST = "currencies_conversion_list"

class UserPreferencesRepository @Inject constructor(
    val sharedPreferences: SharedPreferences,
    val moshi: Moshi
) {
    fun saveCurrenciesData(key: String, currencies: ArrayList<Currency>) {
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        val json = getJsonAdapter().toJson(currencies)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    fun getCurrenciesData(key: String): ArrayList<Currency>? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getString(
                key, ""
            )?.let { getJsonAdapter().fromJson(it) }
        }
        return null
    }

    fun saveCurrenciesConversionData(key: String, data: CurrencyConversionData) {
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        val json = moshi.objectToJson(data)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    inline fun <reified T> getCurrenciesConversionData(key: String): T? {
        if (sharedPreferences.contains(key)) {
            val data = sharedPreferences.getString(
                key, ""
            )
            data?.let {
                return moshi.jsonToObject<T>(data)
            }
        }
        return null
    }

    fun saveLongValue(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        prefsEditor.putLong(key, value)
        prefsEditor.apply()
    }

    fun getLongValue(key: String): Long {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getLong(
                key, 0L
            )
        }
        return 0L
    }

    private fun getJsonAdapter(): JsonAdapter<ArrayList<Currency>> {
        val type = Types.newParameterizedType(
            List::class.java,
            Currency::class.java
        )
        return moshi.adapter(type)
    }
}