package avi.sample.githubstars.ui.main

import androidx.paging.PagingData
import avi.sample.githubstars.base.State
import avi.sample.githubstars.datamodel.Stars
import kotlinx.coroutines.flow.Flow

/**
 * States for this app
 */
sealed class AppState: State {
    /**
     * State depicting app launch.
     */
    object AppLaunch: AppState()

    /**
     * State depicting result of github users fetch.
     */
    data class StarListFetchResult (val apiResult: Flow<PagingData<Stars>>): AppState()
}