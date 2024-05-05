package com.whatmovie.app.compose.util

import androidx.compose.foundation.lazy.LazyListState
import timber.log.Timber

internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem != null && lastVisibleItem.index != 0 && lastVisibleItem.index >= this.layoutInfo.totalItemsCount - buffer
}