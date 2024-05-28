package com.example.coinranking.presentation.allCoin

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.data.repositories.APIRepositories

class CoinsPagingDataSource(
    private val service: APIRepositories,
    private val searchQuery: String
) :
    PagingSource<Int, CoinResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinResponse> {
        try {
            val pageNumber = params.key ?: 1
            val nextPageNumber: Int = pageNumber + 1
            val response = service.getCoins(pageNumber, searchQuery)

            if (response.isEmpty()) {
                return LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = null
                )
            }

            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            return LoadResult.Error(
                Throwable(message = e.message)
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CoinResponse>): Int {
        return 1
    }
}