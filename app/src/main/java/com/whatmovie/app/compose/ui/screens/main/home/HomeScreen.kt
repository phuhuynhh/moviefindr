package com.whatmovie.app.compose.ui.screens.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.AppTheme
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.domain.models.NoInternetException
import com.whatmovie.app.compose.extensions.collectAsEffect
import com.whatmovie.app.compose.ui.base.BaseDestination
import com.whatmovie.app.compose.ui.component.MovieBackground
import com.whatmovie.app.compose.ui.component.error.ErrorMessage
import com.whatmovie.app.compose.ui.component.messagebar.ContentWithMessageBar
import com.whatmovie.app.compose.ui.component.messagebar.MessageBarPosition
import com.whatmovie.app.compose.ui.component.messagebar.MessageBarState
import com.whatmovie.app.compose.ui.component.messagebar.rememberMessageBarState
import com.whatmovie.app.compose.ui.models.UiMovie
import com.whatmovie.app.compose.ui.screens.main.home.movie.EmptySearchResultBody
import com.whatmovie.app.compose.ui.screens.main.home.movie.MovieList
import com.whatmovie.app.compose.ui.showToast

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
) {
    val messageBarState = rememberMessageBarState()

    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }
    viewModel.navigator.collectAsEffect { destination ->
        navigator(destination)
    }


    val searchText by viewModel.searchText.collectAsState()
    val isLoadMore: Boolean by viewModel.isLoadMore.collectAsStateWithLifecycle()
    val listMovieState: ListMovieState by viewModel.uiModels.collectAsStateWithLifecycle()

    viewModel.isOnline.collectAsEffect {
        if (!it) {
            messageBarState.addError(NoInternetException)
        } else {
            messageBarState.clear()
        }
    }


    HomeScreenContent(
        moviesState = listMovieState,
        messageState = messageBarState,
        searchText = searchText,
        onSearchTextChange = viewModel::onSearchTextChange,
        isLoadMore = isLoadMore,
        onRetry = viewModel::loadData,
        onItemClick = viewModel::navigateToDetail,
        onLoadMore = viewModel::loadMore,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    moviesState: ListMovieState,
    messageState: MessageBarState,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isLoadMore: Boolean,
    onRetry: () -> Unit,
    onLoadMore: () -> Unit,
    onItemClick: (UiMovie) -> Unit,
) {
    MovieBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { paddingValues ->
            ContentWithMessageBar(
                messageBarState = messageState,
                position = MessageBarPosition.BOTTOM,
                showCopyButton = false,
                successMaxLines = 2,
                errorMaxLines = 2
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    SearchBar(searchQuery = searchText, onSearchQueryChange = onSearchTextChange)
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .fillMaxSize()
                    ) {
                        MoviesHeader(searchText = searchText)
                        if (moviesState.error != null) {
                            ErrorMessage(
                                error = moviesState.error,
                                onRetry = onRetry
                            )
                        } else if (moviesState.items.isEmpty()) {
                            if (searchText.isNotEmpty()) {
                                EmptySearchResultBody(
                                    onInterestsClick = { onSearchTextChange("") },
                                    searchQuery = searchText
                                )
                            }
                        } else {
                            MovieList(
                                moviesState = moviesState,
                                onItemClick = onItemClick,
                                isLoadMore = isLoadMore,
                                onLoadMore = onLoadMore,
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MoviesHeader(
    searchText: String
) {
    Text(
        text = buildAnnotatedString {
            if (searchText.isEmpty()) {
                withStyle(
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        .toSpanStyle()
                ) {
                    append(stringResource(id = R.string.trending))
                }
            } else {
                withStyle(
                    style = MaterialTheme.typography.titleMedium.toSpanStyle()
                ) {
                    append(stringResource(id = R.string.search_query, searchText))
                }
                withStyle(
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        .toSpanStyle()
                ) {
                    append(" \"${searchText}\"")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreenContent(
            moviesState = ListMovieState(
                items = listOf(
                    UiMovie(
                        title = "Avengers: Endgame",
                        year = "2019-04-24",
                        image = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                        voteAverage = 8.255
                    ), UiMovie(
                        title = "Avengers: Endgame",
                        year = "2019-04-24",
                        image = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                        voteAverage = 8.255
                    ), UiMovie(
                        title = "Avengers: Endgame",
                        year = "2019-04-24",
                        image = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                        voteAverage = 8.255
                    )
                ),
                isLoading = true
            ),
            messageState = rememberMessageBarState(),
            searchText = "123",
            onSearchTextChange = {},
            isLoadMore = true,
            onRetry = {},
            onLoadMore = {},
            onItemClick = {},
        )
    }
}
