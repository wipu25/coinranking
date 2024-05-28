package com.example.coinranking.data.models

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.coinranking.presentation.ui.theme.Black
import com.google.gson.annotations.SerializedName
import kotlin.math.pow

data class CoinResponse(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("symbol") var symbol: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("color") private var color: String? = null,
    @SerializedName("iconUrl") var iconUrl: String? = null,
    @SerializedName("marketCap") private var marketCap: String? = "0",
    @SerializedName("price") private var price: String? = "0.0",
    @SerializedName("btcPrice") var btcPrice: String? = null,
    @SerializedName("listedAt") var listedAt: Int? = null,
    @SerializedName("change") var change: String? = "0.0",
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("sparkline") var sparkline: ArrayList<String> = arrayListOf(),
    @SerializedName("coinrankingUrl") var coinRankingUrl: String? = null,
    @SerializedName("24hVolume") var volume24Hr: String? = null
) {
    fun marketCapPrice(): String {
        if (marketCap.isNullOrEmpty()) {
            return "0.0"
        }
        val price = marketCap!!.split('.')[0]
        val length = price.length
        val num = price.toDouble()
        return when {
            length > 10 -> "${String.format("%.2f", num / 10.0.pow(12.0))} trillion"
            length > 7 -> "${String.format("%.2f", num / 10.0.pow(9.0))} billion"
            length > 4 -> "${String.format("%.2f", num / 10.0.pow(6.0))} million"
            else -> marketCap!!
        }
    }

    fun twoDecimalPrice(decimal: Int): String {
        if (price.isNullOrEmpty()) {
            return "0.0"
        }
        return String.format("%.${decimal}f", price!!.toDouble())
    }

    fun colorFormat(): Color {
        try {
            if (color.isNullOrEmpty()) {
                return Black
            }
            return Color(color!!.toColorInt())
        } catch (e: Exception) {
            return Black
        }
    }
}