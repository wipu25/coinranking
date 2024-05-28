package com.example.coinranking.presentation.allCoin.components.coin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinranking.R
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.presentation.allCoin.components.PriceChange
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.utilities.customShadow

@Composable
fun CoinItem(item: CoinResponse, onSelectCoin: (CoinResponse) -> Unit) {
    val isSvg = item.iconUrl?.split('.')?.last() == "svg"
    Row(modifier = Modifier
        .padding(vertical = 6.dp, horizontal = 8.dp)
        .customShadow(offsetY = 2.dp, offsetX = 1.dp, cornersRadius = 8.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onSelectCoin(item) }
        .background(color = MaterialTheme.colorScheme.surfaceContainer)
        .padding(horizontal = 16.dp, vertical = 21.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = if (isSvg) ImageRequest.Builder(LocalContext.current)
                .data(item.iconUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build() else item.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(Modifier.padding(bottom = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name ?: "",
                    style = Typography.headlineMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = "${stringResource(id = R.string.dollar_price)}${
                        item.twoDecimalPrice(5)
                    }",
                    style = Typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.weight(1F), text = item.symbol ?: "")
                PriceChange(item.change)
            }
        }
    }
}