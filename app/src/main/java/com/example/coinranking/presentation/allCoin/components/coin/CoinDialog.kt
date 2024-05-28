package com.example.coinranking.presentation.allCoin.components.coin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinranking.R
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.presentation.ui.theme.CustomSpanStyle
import com.example.coinranking.presentation.ui.theme.Grey
import com.example.coinranking.presentation.ui.theme.LightBlue
import com.example.coinranking.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDialog(showBottomSheet: Boolean,updateShowBottomSheet: () -> Unit,selectedCoin: CoinResponse?,isLoading: Boolean,description: String) {
    val sheetState = rememberModalBottomSheetState()
    if (showBottomSheet) {
        val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding().value.dp
        ModalBottomSheet(
            modifier = Modifier.padding(bottom = bottomPadding),
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { updateShowBottomSheet() },
        ) {
            val uriHandler = LocalUriHandler.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoinHeader(selectedCoin)
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionText(isLoading,description)
                }
                if (selectedCoin?.coinRankingUrl != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    TextButton(onClick = { uriHandler.openUri(selectedCoin.coinRankingUrl!!) }) {
                        Text(stringResource(id = R.string.go_to_website), color = LightBlue)
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionText(isLoading: Boolean,description: String) {
    //todo: can we check by null?
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp), color = LightBlue)
    } else {
        Text(
            text = description,
            style = Typography.bodyMedium,
            color = Grey
        )
    }
}

@Composable
fun CoinHeader(item: CoinResponse?) {
    val isSvg = item?.iconUrl?.split('.')?.last() == "svg"
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = if (isSvg) ImageRequest.Builder(LocalContext.current)
                .data(item!!.iconUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build() else item!!.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1.0F)) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = CustomSpanStyle.headSemiLarge.plus(
                        SpanStyle(
                            color = item!!.colorFormat()
                        )
                    )
                ) {
                    append(item!!.name)
                }
                withStyle(style = CustomSpanStyle.bodyLarge.copy(color = MaterialTheme.colorScheme.tertiary)) {
                    append(" (${item!!.symbol})")
                }
            })
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = CustomSpanStyle.bodySmall) {
                    append(stringResource(id = R.string.price))
                }
                withStyle(style = CustomSpanStyle.labelSmall) {
                    append(
                        " ${stringResource(id = R.string.dollar_price)} ${
                            item!!.twoDecimalPrice(2)
                        }"
                    )
                }
            }, color = MaterialTheme.colorScheme.tertiary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = CustomSpanStyle.bodySmall) {
                    append(stringResource(id = R.string.market_cap))
                }
                withStyle(style = CustomSpanStyle.labelSmall) {
                    append(
                        " ${stringResource(id = R.string.dollar_price)} ${
                            item!!.marketCapPrice()
                        }"
                    )
                }
            }, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}