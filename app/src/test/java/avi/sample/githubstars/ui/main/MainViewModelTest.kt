package avi.sample.githubstars.ui.main

import avi.sample.BaseViewModelTest
import avi.sample.githubstars.data.FakeGitHubStarRepository
import avi.sample.githubstars.data.GitHubStarListRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: MainViewModel
    lateinit var fakeRepo: FakeGitHubStarRepository

    @Before
    fun setUp() {
        fakeRepo = FakeGitHubStarRepository()
        viewModel = MainViewModel(fakeRepo, testDispatcher)
    }

    @Test
    fun `verify - initial state`() {
        assertEquals(viewModel.createInitialState(), viewModel.uiState.value)
    }

    @Test
    fun `test handleEvent - refresh`() = runBlocking {
        viewModel.setEvent(UIEvent.Refresh)
        assertEquals(UIEffects.Refresh, viewModel.effect.first())
    }

    @Test
    fun `test handleEvent - retry`() = runBlocking {
        viewModel.setEvent(UIEvent.Retry)
        assertEquals(UIEffects.Retry, viewModel.effect.first())
    }

    @Test
    fun `test handleEvent - fetch`() = runBlocking {
        viewModel.setEvent(UIEvent.FetchStarList())
        assertTrue(viewModel.uiState.value is AppState.StarListFetchResult)
    }
}