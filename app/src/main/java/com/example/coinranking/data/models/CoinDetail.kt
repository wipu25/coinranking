package com.example.coinranking.data.models

import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var coinDetail: DetailData? = DetailData()
)


data class DetailData(
    @SerializedName("coin") var coin: CoinDetail = CoinDetail()
)

data class CoinDetail(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String = "",
)