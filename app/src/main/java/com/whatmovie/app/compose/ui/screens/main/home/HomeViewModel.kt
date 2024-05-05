package com.whatmovie.app.compose.ui.screens.main.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.whatmovie.app.compose.data.remote.services.NetworkObserver
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.domain.exceptions.DataError
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.models.MovieInfo
import com.whatmovie.app.compose.domain.models.PaginatedList
import com.whatmovie.app.compose.domain.usecases.GetMoviesUseCase
import com.whatmovie.app.compose.ui.base.BaseViewModel
import com.whatmovie.app.compose.ui.component.error.UiText
import com.whatmovie.app.compose.ui.component.error.asUiText
import com.whatmovie.app.compose.ui.models.UiMovie
import com.whatmovie.app.compose.ui.models.toUiModel
import com.whatmovie.app.compose.ui.screens.main.MainDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEBOUNCE_INTERVAL = 500L

// Need this to not trigger the flow trigger when return from detail screen.
private const val QUERY_STRING = "QUERY_STRING"

@HiltViewModel
open class HomeViewModel @Inject constructor(
    val getMoviesUseCase: GetMoviesUseCase,
    private val dispatchersProvider: DispatchersProvider,
    private val networkObserver: NetworkObserver,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _isLoadMore = MutableStateFlow(false)
    val isLoadMore = _isLoadMore.asStateFlow()

    private val _uiModels = MutableStateFlow(ListMovieState())


    val isOnline = networkObserver.isOnline.distinctUntilChanged().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    val uiModels: StateFlow<ListMovieState> =
        searchText
            .debounce(DEBOUNCE_INTERVAL)
            .distinctUntilChanged()
            .onEach { _isSearching.update { true } }
            .flatMapLatest { searchQuery ->
                _uiModels.emit(_uiModels.value.copy(isLoading = true))
                loadData(searchQuery = searchQuery, page = 1)
                flowOf(searchQuery)
            }
            .combine(_uiModels) { search, uiModels ->
                uiModels.copy(searchQuery = search)
            }
            .onEach { _isSearching.update { false } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                _uiModels.value
            )

    init {
        viewModelScope.launch {
            loadData()
            _searchText.emit(savedStateHandle.get<String>(QUERY_STRING) ?: "")
        }
    }

    fun loadMore() {
        loadData(searchQuery = uiModels.value.searchQuery, uiModels.value.currentPage + 1)

    }

    fun loadData(searchQuery: String = searchText.value, page: Int = uiModels.value.currentPage) {
        getMoviesUseCase(searchQuery, page)
            .injectLoading()
            .injectLoadMore()
            .onEach { result ->
                updateListMovieState(result)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }


    private suspend fun updateListMovieState(result: DataResult<PaginatedList<MovieInfo>, DataError>) {
        when (result) {
            is DataResult.Error -> {
                _uiModels.emit(
                    ListMovieState(
                        isLoading = true,
                        currentPage = 1,
                        error = result.error.asUiText()

                    )
                )
            }

            is DataResult.Success -> {
                val data = result.data
                val currentState = uiModels.value
                val successResult: ListMovieState
                if (data.totalResults == 0) {
                    successResult = _uiModels.value.copy(
                        items = listOf(),
                        error = null
                    )
                } else {
                    val movieUI = data.results.map { it.toUiModel() }
                    successResult = ListMovieState(
                        items = if (data.page > currentState.currentPage) _uiModels.value.items.plus(movieUI) else movieUI,
                        currentPage = data.page,
                        totalPage = data.totalPages,
                        isLoading = false,
                        error = null
                    )
                }

                _uiModels.emit(successResult)

            }
        }


    }


    private fun <T> Flow<T>.injectLoadMore(): Flow<T> = this
        .onStart { _isLoadMore.emit(true) }
        .onCompletion { _isLoadMore.emit(false) }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        savedStateHandle[QUERY_STRING] = text
    }

    fun navigateToDetail(uiMovie: UiMovie) {
        launch { _navigator.emit(MainDestination.MovieDetail.createRoute(uiMovie.id)) }
    }
}

data class ListMovieState(
    val searchQuery: String = "",
    val items: List<UiMovie> = listOf(),
    val isFull: Boolean = false,
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val currentPage: Int = 1,
    val totalPage: Int = 100,
)
