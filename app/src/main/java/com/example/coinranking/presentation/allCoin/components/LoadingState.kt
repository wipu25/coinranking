package com.example.coinranking.presentation.allCoin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.coinranking.R
import com.example.coinranking.presentation.ui.theme.Black
import com.example.coinranking.presentation.ui.theme.Grey
import com.example.coinranking.presentation.ui.theme.LightBlue
import com.example.coinranking.presentation.ui.theme.Typography

@Composable
fun LoadingState(loadState: LoadState, onRetry: () -> Unit) {
    when (loadState) {
        is LoadState.Error -> {
            NoData(onRetry)
        }

        is LoadState.Loading -> {
            Loading()
        }

        else -> {}
    }
}

@Composable
fun NoResult() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.sorry), style = Typography.headlineLarge)
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.no_result),
            style = Typography.bodyLarge,
            color = Grey
        )
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = LightBlue)
    }
}

@Composable
fun NoData(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = stringResource(id = R.string.no_data),
            color = Black
        )
        TextButton(onClick = onRetry) {
            Text(text = stringResource(id = R.string.try_again), color = LightBlue)
        }
    }
}