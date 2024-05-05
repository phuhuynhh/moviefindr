package com.whatmovie.app.compose.ui

import android.content.Context
import com.whatmovie.app.compose.R
import com.whatmovie.app.compose.extensions.showToast
import timber.log.Timber

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        else -> message
    } ?: context.getString(R.string.error_generic)
}

fun Throwable.showToast(context: Context): Unit {
    Timber.e(userReadableMessage(context))
    return context.showToast(userReadableMessage(context), )
}
