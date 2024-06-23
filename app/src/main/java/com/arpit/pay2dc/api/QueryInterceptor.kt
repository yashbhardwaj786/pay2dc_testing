package com.arpit.pay2dc.api

import com.arpit.pay2dc.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class QueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val ongoing = chain.request()
        val originalHttpUrl: HttpUrl = ongoing.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("app_id", BuildConfig.API_KEY)
            .build()
        val requestBuilder: Request.Builder = ongoing.newBuilder()
            .url(url)
        return chain.proceed(requestBuilder.build())
    }
}