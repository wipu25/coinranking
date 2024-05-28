package com.example.coinranking.data.repositories

import android.util.Log
import com.example.coinranking.api.APIService
import com.example.coinranking.data.models.CoinDetail
import com.example.coinranking.data.models.CoinResponse

class APIRepositories(
    private val apiInterface: APIService
) {
    suspend fun getCoins(page: Int, searchQuery: String): List<CoinResponse> {
        val result =
            apiInterface.getCoinsByPage(offSet = (page - 1) * 20, search = searchQuery)
        if (result.code() != 200) {
            Log.d("Error", result.message())
            throw Exception("Error receiving coins")
        }
        return result.body()!!.data!!.coins
    }

    suspend fun getDetails(uuid: String): CoinDetail {
        val result = apiInterface.getCoinDetail(uuid)
        if (result.code() != 200) {
            Log.d("Error", result.message())
            throw Exception("Error receiving coins")
        }
        return result.body()!!.coinDetail!!.coin
    }
}