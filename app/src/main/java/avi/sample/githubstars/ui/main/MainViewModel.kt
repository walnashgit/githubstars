package avi.sample.githubstars.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import avi.sample.githubstars.base.BaseViewModel
import avi.sample.githubstars.data.GitHubStarListRepo
import avi.sample.githubstars.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "MainViewModel"

/**
 * ViewModel class for handling app's state and events and data fetch.
 */
@HiltViewModel
open class MainViewModel @Inject constructor(
    private val starListRepository: @JvmSuppressWildcards GitHubStarListRepo,
    private val dispatcher: DispatcherProvider) :
    BaseViewModel<AppState, UIEvent, UIEffects>(
        dispatcher
    ) {

    init {
    }
    override fun createInitialState(): AppState {
        return AppState.AppLaunch
    }

    override fun handleEvent(events: UIEvent) {
        when (events) {
            is UIEvent.FetchStarList -> {
                getStarsList(events.query)
            }
            UIEvent.Refresh -> {
                setEffect(UIEffects.Refresh)
            }
            UIEvent.Retry -> {
                setEffect(UIEffects.Retry)
            }
            is UIEvent.UserClicked -> {
                setEffect(UIEffects.OpenStarDetailFragment(events.imageView, events.star))
            }
        }
    }

    private fun getStarsList(query: String) {
        viewModelScope.launch(dispatcher.main) {
            val result = starListRepository.getDataFromGitHub(query).cachedIn(viewModelScope)
            setAppState(AppState.StarListFetchResult(result))
        }
    }
}