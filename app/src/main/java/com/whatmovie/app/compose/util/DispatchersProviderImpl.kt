package com.whatmovie.app.compose.util

import com.whatmovie.app.compose.domain.DispatchersProvider
import kotlinx.coroutines.Dispatchers

class DispatchersProviderImpl : DispatchersProvider {

    override val io = Dispatchers.IO

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default
}
