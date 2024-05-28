package com.example.coinranking.presentation.allCoin

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.coinranking.R
import com.example.coinranking.presentation.allCoin.components.InviteItem
import com.example.coinranking.presentation.allCoin.components.LoadingState
import com.example.coinranking.presentation.allCoin.components.NoResult
import com.example.coinranking.presentation.allCoin.components.SearchRow
import com.example.coinranking.presentation.allCoin.components.TopRank
import com.example.coinranking.presentation.allCoin.components.coin.CoinDialog
import com.example.coinranking.presentation.allCoin.components.coin.CoinItem
import com.example.coinranking.presentation.ui.theme.LightBlue
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.presentation.ui.theme.White
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllCoinsScreen(allCoinsViewModel: AllCoinsViewModel = getViewModel()) {
    val coinsItem = allCoinsViewModel.allItems.collectAsLazyPagingItems()
    val allCoinsState by allCoinsViewModel.state.collectAsState()
    val isSearching = allCoinsState.searchText.isNotEmpty()
    val searchItem = allCoinsState.searchItem.collectAsLazyPagingItems()
    val list = if (!isSearching) coinsItem else searchItem
    val loadState = if (!isSearching) coinsItem.loadState else searchItem.loadState
    val state = rememberPullToRefreshState()
    val isOrientation =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            coinsItem.refresh()
            state.endRefresh()
        }
    }

    CoinDialog(
        allCoinsState.showBottomSheet,
        allCoinsViewModel::updateShowBottomSheet,
        allCoinsState.selectedCoins,
        allCoinsState.isDescriptionLoading,
        allCoinsState.selectedDescription
    )
    Box(
        Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .nestedScroll(state.nestedScrollConnection)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isOrientation) 3 else 1),
            horizontalArrangement = Arrangement.Start,
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    SearchRow(
                        allCoinsState.searchText,
                        isSearching,
                        allCoinsViewModel::clear,
                        allCoinsViewModel::updateSearchText
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (!isSearching && list.itemCount > 0) {
                        TopRank(list.itemSnapshotList.slice(0..2),allCoinsViewModel::selectCoin)
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.coin_list_title),
                        style = Typography.headlineMedium,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadingState(
                    loadState = loadState.refresh,
                    onRetry = { list.retry() }
                )
            }
            if (loadState.append.endOfPaginationReached && list.itemCount == 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    NoResult()
                }
            } else {
                val startIndex = if (!isSearching) 3 else 0
                val checkInvite = if (!isSearching) -2 else 1
                for (index in startIndex until list.itemCount) {
                    if (allCoinsViewModel.checkInvitePos(index + checkInvite)) {
                        item {
                            InviteItem()
                        }
                    }
                    item {
                        CoinItem(item = list[index]!!,allCoinsViewModel::selectCoin)
                    }
                }
            }
            item {
                LoadingState(
                    loadState = loadState.append,
                    onRetry = { if (isSearching) searchItem.retry() else coinsItem.retry() }
                )
            }
        }
        PullToRefreshContainer(
            containerColor = White,
            contentColor = LightBlue,
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}