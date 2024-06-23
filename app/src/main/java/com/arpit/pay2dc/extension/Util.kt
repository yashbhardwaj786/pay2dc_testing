package com.arpit.pay2dc.extension

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun View?.showSnackbar(error: String, action: () -> Unit) {
    this?.let {
        val snackbar = Snackbar.make(
            it, error,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Retry") {
            action()
        }
        snackbar.setActionTextColor(Color.RED)
        snackbar.show()
    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val cal: Calendar = Calendar.getInstance()
//    cal.add(Calendar.DATE, -1)
    return dateFormat.format(cal.time)
}

inline fun <reified T> Moshi.objectToJson(data: T): String =
    adapter(T::class.java).toJson(data)

inline fun <reified T> Moshi.jsonToObject(json: String): T? =
    adapter(T::class.java).fromJson(json)
