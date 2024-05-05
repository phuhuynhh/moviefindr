package com.whatmovie.app.compose.ui.screens.main.detail

import androidx.lifecycle.viewModelScope
import com.whatmovie.app.compose.domain.DispatchersProvider
import com.whatmovie.app.compose.domain.models.DataResult
import com.whatmovie.app.compose.domain.usecases.GetMovieDetailUseCase
import com.whatmovie.app.compose.ui.base.BaseViewModel
import com.whatmovie.app.compose.ui.component.error.UiText
import com.whatmovie.app.compose.ui.component.error.asUiText
import com.whatmovie.app.compose.ui.models.UiMovieDetail
import com.whatmovie.app.compose.ui.models.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel() {
    private val _uiModels = MutableStateFlow(UiMovieDetailState())

    val uiModels = _uiModels.asStateFlow()

    fun getMovieDetail(movieId: Long) {
        if (!_uiModels.value.isLoading) return
        getMovieDetailUseCase(movieId = movieId)
            .onEach { result ->
                val state = when (result) {
                    is DataResult.Error ->
                        UiMovieDetailState(
                            item = UiMovieDetail(),
                            error = result.error.asUiText(),
                            isLoading = true
                        )

                    is DataResult.Success ->
                        UiMovieDetailState(
                            item = result.data.toUiModel(),
                            error = null,
                            isLoading = false
                        )
                }
                _uiModels.emit(state)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }
}

data class UiMovieDetailState(
    val item: UiMovieDetail = UiMovieDetail(),
    val isLoading: Boolean = true,
    val error: UiText? = null,
)
