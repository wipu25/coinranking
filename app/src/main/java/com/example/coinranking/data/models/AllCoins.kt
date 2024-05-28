package com.example.coinranking.data.models

import com.google.gson.annotations.SerializedName

data class AllCoins(
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: Data? = Data()
)

data class Data(
    @SerializedName("stats") var stats: Stats? = Stats(),
    @SerializedName("coins") var coins: ArrayList<CoinResponse> = arrayListOf()
)

data class Stats(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalCoins") var totalCoins: Int? = null,
    @SerializedName("totalMarkets") var totalMarkets: Int? = null,
    @SerializedName("totalExchanges") var totalExchanges: Int? = null,
    @SerializedName("totalMarketCap") var totalMarketCap: String? = null,
    @SerializedName("total24hVolume") var total24hVolume: String? = null
)