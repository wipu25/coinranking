package com.example.coinranking.presentation.allCoin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coinranking.data.models.CoinResponse
import com.example.coinranking.data.repositories.APIRepositories
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class AllCoinsViewModel(
    private val apiRepositories: APIRepositories
) : ViewModel() {
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var _allItems: Flow<PagingData<CoinResponse>> = flow { }
    private var _searchItems: MutableStateFlow<Flow<PagingData<CoinResponse>>> =
        MutableStateFlow(flow {})
    private val _showBottomSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isDescriptionLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _selectedCoins: MutableStateFlow<CoinResponse?> = MutableStateFlow(null)
    private val _selectedDescription: MutableStateFlow<String> = MutableStateFlow("")
    private var _invitePos: MutableList<Int> = mutableListOf(5)
    private var _inviteTempPos: Int = 5
    private val _state = MutableStateFlow(AllCoinsViewState())

    val state: StateFlow<AllCoinsViewState>
        get() = _state
    val allItems: Flow<PagingData<CoinResponse>>
        get() = _allItems

    init {
        getCoins()
        viewModelScope.launch {
            _searchText.debounce(500).collect {
                if(_searchText.value.isNotEmpty()) {
                    searchCoins()
                }
            }
        }

        viewModelScope.launch {
            combine(
                _searchText,
                _searchItems,
                _showBottomSheet,
                _isDescriptionLoading,
                _selectedCoins,
                _selectedDescription,
            ) {
                array ->
                AllCoinsViewState(
                    array[0] as String,
                    array[1] as Flow<PagingData<CoinResponse>>,
                    array[2] as Boolean,
                    array[3] as Boolean,
                    array[4] as CoinResponse?,
                    array[5] as String,
                )
            }.collect {
                _state.value = it
            }
        }
    }

    fun checkInvitePos(index: Int): Boolean {
        if (!_invitePos.contains(index)) {
            return false
        }
        if (_invitePos.last() == index) {
            incrementInvitePos()
        }
        return true
    }

    private fun incrementInvitePos() {
        _inviteTempPos *= 2
        _invitePos.add(_inviteTempPos - _invitePos.size)
    }

    fun selectCoin(item: CoinResponse) {
        _selectedCoins.value = item
        updateShowBottomSheet()
        if (item.uuid == null) {
            return
        }
        viewModelScope.launch {
            _isDescriptionLoading.value = true
            _selectedDescription.value = apiRepositories.getDetails(item.uuid!!).description
            _isDescriptionLoading.value = false
        }
    }

    fun updateShowBottomSheet() {
        _showBottomSheet.value = !_showBottomSheet.value
    }

    fun updateSearchText(value: String) {
        _searchText.value = value
    }


    fun clear() {
        _searchText.value = ""
    }

    private fun getCoins() {
        _allItems = Pager(
            pagingSourceFactory = {
                CoinsPagingDataSource(
                    apiRepositories,
                    ""
                )
            },
            config = PagingConfig(pageSize = 20)
        ).flow.cachedIn(viewModelScope)
    }

    private fun searchCoins() {
        _searchItems.value = Pager(
            pagingSourceFactory = {
                CoinsPagingDataSource(
                    apiRepositories,
                    _searchText.value
                )
            },
            config = PagingConfig(pageSize = 20)
        ).flow.cachedIn(viewModelScope)
    }
}

data class AllCoinsViewState(
    val searchText: String = "",
    val searchItem: Flow<PagingData<CoinResponse>> = flow {  },
    val showBottomSheet: Boolean = false,
    val isDescriptionLoading: Boolean = false,
    val selectedCoins: CoinResponse? = null,
    val selectedDescription: String = "",
)