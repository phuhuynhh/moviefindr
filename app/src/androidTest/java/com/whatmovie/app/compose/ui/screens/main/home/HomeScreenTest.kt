package com.whatmovie.app.compose.ui.screens.main.home

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.compose.AppTheme
import com.whatmovie.app.compose.data.remote.services.NetworkObserver
import com.whatmovie.app.compose.domain.usecases.GetMoviesUseCase
import com.whatmovie.app.compose.test.MockUtil.successMovieInfoDataResult
import com.whatmovie.app.compose.test.TestDispatchersProvider
import com.whatmovie.app.compose.ui.base.BaseDestination
import com.whatmovie.app.compose.ui.screens.MainActivity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockGetMoviesUseCase: GetMoviesUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var networkObserver: NetworkObserver
    private lateinit var mockContext: Context

    private lateinit var viewModel: HomeViewModel
    private var expectedDestination: BaseDestination? = null


    @Before
    fun setUp() {
        mockContext = mockk<Context>()
        setupNetwork()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun setupNetwork() {
        networkObserver = NetworkObserver(mockContext)

        // Create a test coroutine
        val testCoroutine = kotlinx.coroutines.test.TestCoroutineScope()

        // Set up the flow with a test coroutine
        val flow: Flow<Boolean> = callbackFlow {
            testCoroutine.launch {
                send(true)
            }
            // Await close
            awaitClose()
        }

        // Use the flow in the NetworkObserver
        networkObserver.isOnline = flow



        every { mockGetMoviesUseCase() } returns flowOf(successMovieInfoDataResult)
        every { savedStateHandle.get<String>("QUERY_STRING") } returns ""
        every { savedStateHandle["QUERY_STRING"] = "" } returns Unit


        viewModel = HomeViewModel(
            mockGetMoviesUseCase,
            TestDispatchersProvider,
            networkObserver,
            savedStateHandle
        )
    }


    @Test
    fun when_entering_the_Home_screen__it_shows_UI_correctly() = initComposable {
        onNodeWithTag("Movie Findr").assertIsDisplayed()
    }

//    @Test
//    fun when_loading_list_item_successfully__it_shows_the_list_item_correctly() = initComposable {
//        onNodeWithTag("1000").assertIsDisplayed()
//        onNodeWithTag("1001").assertIsDisplayed()
//        onNodeWithTag("1002").assertIsDisplayed()
//    }
//
//    @Test
//    fun when_clicking_on_a_list_item__it_navigates_to_Second_screen() = initComposable {
//        onNodeWithTag("1000").performClick()
//
//        assertEquals(expectedDestination, MainDestination.MovieDetail)
//    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit
    ) {
        composeRule.activity.setContent {
            AppTheme {
                HomeScreen(
                    viewModel = viewModel,
                    navigator = { destination -> expectedDestination = destination }
                )
            }
        }
        testBody(composeRule)
    }
}
