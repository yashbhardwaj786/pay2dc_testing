package com.arpit.pay2dc.util

import android.content.Context
import android.net.ConnectivityManager
import com.arpit.pay2dc.MyApplication

fun checkInternet(): Boolean {
    val cm = MyApplication.getInstance()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}