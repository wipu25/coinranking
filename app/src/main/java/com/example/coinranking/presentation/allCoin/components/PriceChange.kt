package com.example.coinranking.presentation.allCoin.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.coinranking.presentation.ui.theme.DownColor
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.presentation.ui.theme.UpColor

@Composable
fun PriceChange(price: String?) {
    val realPrice = price ?: "0,0"
    val isNegative = realPrice.contains('-')
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.AutoMirrored.Outlined.ArrowBack,
            "arrow",
            modifier = Modifier
                .rotate(if (isNegative) 270F else 90F)
                .size(12.dp),
            tint = if (isNegative) DownColor else UpColor
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = if (isNegative) realPrice.split('-')[1] else realPrice,
            style = Typography.bodySmall,
            color = if (isNegative) DownColor else UpColor
        )
    }
}