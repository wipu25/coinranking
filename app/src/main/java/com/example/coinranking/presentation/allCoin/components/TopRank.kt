package com.example.coinranking.presentation.allCoin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.coinranking.R
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.presentation.allCoin.components.coin.TopCoinItem
import com.example.coinranking.presentation.ui.theme.Typography

@Composable
fun TopRank(coinsItem: List<CoinResponse?>, onSelectedCoin: (CoinResponse) -> Unit) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseSurface)) {
                append(stringResource(id = R.string.top))
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                append(" ${stringResource(id = R.string.three)} ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseSurface)) {
                append(stringResource(id = R.string.rank))
            }
        }, style = Typography.headlineMedium
    )
    Spacer(modifier = Modifier.height(12.dp))
    //todo: why not loop this top coin item
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TopCoinItem(coinsItem[0] ?: CoinResponse(),onSelectedCoin)
        Spacer(modifier = Modifier.width(8.dp))
        TopCoinItem(coinsItem[1] ?: CoinResponse(),onSelectedCoin)
        Spacer(modifier = Modifier.width(8.dp))
        TopCoinItem(coinsItem[2] ?: CoinResponse(),onSelectedCoin)
    }
    Spacer(modifier = Modifier.height(22.dp))
}