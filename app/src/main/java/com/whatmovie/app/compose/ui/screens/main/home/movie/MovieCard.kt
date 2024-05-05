package com.whatmovie.app.compose.ui.screens.main.home.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.AppTheme
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.ui.component.AnimatedCircularProgressIndicator
import com.whatmovie.app.compose.ui.models.UiMovie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCard(
    uiMovie: UiMovie,
    onClick: (UiMovie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(uiMovie) }
            )
            .heightIn(0.dp, 115.dp)
            .testTag(uiMovie.id.toString())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start

        ) {
            Box(
                modifier = Modifier
                    .weight(1.2f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiMovie.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(id = R.drawable.ic_film_square_placeholder),
                    error = painterResource(id = R.drawable.ic_film_square_placeholder),
                    contentDescription = uiMovie.title,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .weight(2f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    text = uiMovie.title
                )
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    text = uiMovie.year
                )
            }

            AnimatedCircularProgressIndicator(
                currentValue = uiMovie.voteAverage.toFloat(),
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(45.dp)
                    .padding(4.dp),

                )


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    AppTheme {
        MovieCard(
            uiMovie = UiMovie(
                title = "Avengers: Endgame",
                year = "2019-04-24",
                image = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                voteAverage = 8.255
            ),
            onClick = {},
        )
    }
}
