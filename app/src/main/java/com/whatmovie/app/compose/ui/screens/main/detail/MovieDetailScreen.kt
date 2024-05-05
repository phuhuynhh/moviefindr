package com.whatmovie.app.compose.ui.screens.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.AppTheme
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.ui.base.BaseDestination
import com.whatmovie.app.compose.ui.component.AnimatedCircularProgressIndicator
import com.whatmovie.app.compose.ui.component.HyperlinkText
import com.whatmovie.app.compose.ui.component.error.ErrorMessage
import com.whatmovie.app.compose.ui.component.error.UiText
import com.whatmovie.app.compose.ui.models.SampleUiMovieDetail
import com.whatmovie.app.compose.ui.models.UiMovieDetail
import com.whatmovie.app.compose.ui.models.toUiModel
import com.whatmovie.app.compose.ui.screens.main.home.movie.TestTagCoinsLoader
import com.whatmovie.app.compose.util.TextHelper

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
    id: Long,
) {
    LaunchedEffect(key1 = id) {
        viewModel.getMovieDetail(id)
    }
    val movieDetail: UiMovieDetailState by viewModel.uiModels.collectAsStateWithLifecycle()
    SecondScreenContent(
        id = id,
        movieDetail = movieDetail,
        onNavigateBack = {
            navigator(BaseDestination.Up())
        },
        fetchMovieDetail = {
            viewModel.getMovieDetail(id)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    movieDetail: UiMovieDetail,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = movieDetail.title) },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.what_back)
                )
            }
        },
    )
}

@Composable
private fun SecondScreenContent(
    id: Long,
    movieDetail: UiMovieDetailState,
    onNavigateBack: () -> Unit,
    fetchMovieDetail: () -> Unit,
) {
    Scaffold(topBar = {
        MovieTopAppBar(movieDetail = movieDetail.item, navigateBack = onNavigateBack)
    }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (movieDetail.error != null) {
                ErrorMessage(
                    error = movieDetail.error,
                    onRetry = fetchMovieDetail
                )

            } else if (movieDetail.isLoading) {
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
            } else {
                MovieDetail(movieDetail)
            }


            HyperlinkText(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 4.dp),
                fullText = "By using our services are agreeing to our\n" + "Terms and Privacy statement",
                hyperLinks = mutableMapOf(
                    "Terms" to "https://www.themoviedb.org/api-terms-of-use",
                    "Privacy statement" to "https://www.themoviedb.org/privacy-policy"
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Gray
                ),
                linkTextColor = Color.Blue,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun MovieDetail(
    movieDetail: UiMovieDetailState,
    modifier: Modifier = Modifier
) {

    val item = movieDetail.item
    Column(modifier = modifier
        .verticalScroll(rememberScrollState())) {
        MoviePoster(item = item, modifier = modifier)
        MovieDetailsDescription(
            movieDetail = item,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxWidth()
        )
        ProductionCompanyList(companies = movieDetail.item.productionCompanies)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun MoviePoster(
    item: UiMovieDetail,
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.backdropPath)
                .crossfade(true)
                .build(),
            colorFilter = ColorFilter.tint(Color(0xCC444444), BlendMode.Multiply),
            contentScale = ContentScale.FillWidth,
            placeholder = object : Painter() {
                override val intrinsicSize get() = Size(500f, 300f)

                override fun DrawScope.onDraw() {
                    drawRect(Color.DarkGray)
                }
            },
            error = object : Painter() {
                override val intrinsicSize get() = Size(500f, 300f)

                override fun DrawScope.onDraw() {
                    drawRect(Color.DarkGray)
                }
            },
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .height(150.dp)
                .fillMaxWidth(),
        ) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.posterPath)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillHeight,
                    placeholder = painterResource(id = R.drawable.ic_film_placeholder),
                    error = painterResource(id = R.drawable.ic_film_placeholder),
                    contentDescription = item.title,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1 / 3f)
                        .aspectRatio(220f / 330f, matchHeightConstraintsFirst = true)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(2 / 3f)
//                        .background(Color(0xCC888888), RoundedCornerShape(4.dp))
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = Color.White)
                                .toSpanStyle()
                        ) {
                            append(item.title)
                        }
                        withStyle(
                            MaterialTheme.typography.bodyLarge.copy(color = Color.LightGray)
                                .toSpanStyle()
                        ) {
                            append(" (${item.releaseDate.split("-")[0]})")
                        }

                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray)
                                .toSpanStyle()
                        ) {
                            append(TextHelper.convertRuntimeToText(item.runtime))
                        }

                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AnimatedCircularProgressIndicator(
                        currentValue = item.voteAverage.toFloat(),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(3.dp),
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                ).toSpanStyle()
                            ) {
                                append(stringResource(id = R.string.user_score))
                            }
                            if (item.voteCount > 0) {
                                withStyle(
                                    MaterialTheme.typography.bodySmall.copy(color = Color.LightGray).toSpanStyle()
                                ) {
                                    append("\n${item.voteCount} reviews")
                                }
                            }
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                    )
                }

                Text(
                    text = item.genres.joinToString(separator = ", ") { it.name },
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )

            }
        }
    }
}


@Composable
fun MovieDetailsDescription(
    movieDetail: UiMovieDetail,
    modifier: Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showSeeMore by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.titleMedium.copy(color = Gray, fontStyle = FontStyle.Italic)
                        .toSpanStyle()
                ) {
                    append(movieDetail.tagline)
                }

            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
        )

        Text(
            text = stringResource(id = R.string.overview),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 6.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = movieDetail.overview,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isExpanded) Int.MAX_VALUE else 4,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { result ->
                    showSeeMore = result.hasVisualOverflow
                },
            )
            if (showSeeMore) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                                .toSpanStyle()
                        ) {
                            append(stringResource(id = R.string.see_more))
                        }
                    },
                    modifier = Modifier
                        .clickable {
                            isExpanded = !isExpanded
                        }
                )
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun SecondScreenPreview() {
    AppTheme {
        SecondScreenContent(
            id = 1051896,
            movieDetail = UiMovieDetailState(
                item = SampleUiMovieDetail.model1.toUiModel(),
                isLoading = false
            ),
            onNavigateBack = {},
            fetchMovieDetail = {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SecondScreenNetworkErrorPreview() {
    AppTheme {
        SecondScreenContent(
            id = 1051896,
            movieDetail = UiMovieDetailState(
                item = SampleUiMovieDetail.model1.toUiModel(),
                isLoading = false,
                error = UiText.StringResource(id = R.string.no_internet)
            ),
            onNavigateBack = {},
            fetchMovieDetail = {},
        )
    }
}
