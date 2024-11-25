package com.example.coinranking.api

import okhttp3.Interceptor

private const val API_KEY = "api-key"

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain
            .request()
            .newBuilder()
            .header("x-access-token", API_KEY)
            .build()
        return chain.proceed(request)
    }
}