package com.example.coinranking.api

import com.example.coinranking.data.models.AllCoins
import com.example.coinranking.data.models.CoinDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("coins")
    suspend fun getCoinsByPage(
        @Query("limit") limit: Int = 20,
        @Query("offset") offSet: Int = 0,
        @Query("search") search: String = ""
    ): Response<AllCoins>

    @GET("coin/{uuid}")
    suspend fun getCoinDetail(
        @Path("uuid") uuid: String? = null
    ): Response<CoinDetailResponse>
}