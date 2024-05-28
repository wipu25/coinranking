package com.example.coinranking.presentation.allCoin.components.coin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.presentation.allCoin.components.PriceChange
import com.example.coinranking.presentation.ui.theme.Grey
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.utilities.customShadow

@Composable
fun TopCoinItem(item: CoinResponse, onSelectedCoin: (CoinResponse) -> Unit) {
    val isSvg = item.iconUrl?.split('.')?.last() == "svg"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .customShadow(offsetY = 2.dp, offsetX = 1.dp, cornersRadius = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelectedCoin(item) }
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 16.dp)
    ) {
        AsyncImage(
            model = if (isSvg) ImageRequest.Builder(LocalContext.current)
                .data(item.iconUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build() else item.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.symbol ?: "",
            style = Typography.headlineSmall,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.name ?: "", style = Typography.labelSmall, color = Grey)
        Spacer(modifier = Modifier.height(8.dp))
        PriceChange(item.change)
    }
}