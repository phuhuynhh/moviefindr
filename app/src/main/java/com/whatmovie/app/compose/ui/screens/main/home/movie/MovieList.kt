package com.whatmovie.app.compose.ui.screens.main.home.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.ui.models.UiMovie
import com.whatmovie.app.compose.ui.screens.main.home.ListMovieState
import com.whatmovie.app.compose.util.reachedBottom

const val TestTagCoinsLoader = "CoinsLoader"

@Composable
fun MovieList(
    moviesState: ListMovieState,
    onItemClick: (UiMovie) -> Unit,
    isLoadMore: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom(buffer = 3) } }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !isLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (moviesState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .testTag(tag = TestTagCoinsLoader),
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        } else {
            items(moviesState.items) { uiModel ->
                MovieCard(
                    uiMovie = uiModel,
                    onClick = onItemClick,
                )
            }
            if (moviesState.items.isNotEmpty() && isLoadMore) {
                item {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .testTag(tag = TestTagCoinsLoader),
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySearchResultBody(
    onInterestsClick: () -> Unit,
    searchQuery: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.search_result_not_found, searchQuery),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp),
        )
        val interests = stringResource(id = R.string.trending)
        val tryAnotherSearchString = buildAnnotatedString {
            append(stringResource(id = R.string.try_another_search))
            append(" ")
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                ),
            ) {
                pushStringAnnotation(tag = interests, annotation = interests)
                append(interests)
            }
        }
        ClickableText(
            text = tryAnotherSearchString,
            style = MaterialTheme.typography.bodyLarge.merge(
                TextStyle(
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                ),
            ),
            modifier = Modifier
                .padding(start = 36.dp, end = 36.dp, bottom = 24.dp)
                .clickable {},
        ) { offset ->
            tryAnotherSearchString.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()
                ?.let {
                    onInterestsClick()
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListPreview() {
    AppTheme {
        MovieList(
            moviesState = ListMovieState(
                isLoading = false,
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
                )
            ),
            isLoadMore = true,
            onItemClick = {},
            onLoadMore = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptySearchBodyPreview() {
    AppTheme {
        EmptySearchResultBody(onInterestsClick = { }, searchQuery = "I don't know")
    }
}
