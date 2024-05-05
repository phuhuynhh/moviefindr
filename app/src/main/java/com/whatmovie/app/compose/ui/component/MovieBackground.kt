package com.whatmovie.app.compose.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.whatmovie.app.compose.R

@Composable
fun MovieBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.inverseSurface,
        tonalElevation = 0.dp,
        modifier = modifier
            .fillMaxSize()
            .testTag(stringResource(id = R.string.app_name)),
    ) {
        content()
    }
}