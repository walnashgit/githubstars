package avi.sample.githubstars.ui.star

import android.util.Log
import androidx.lifecycle.*
import avi.sample.githubstars.data.GitHubStarDetailRepo
import avi.sample.githubstars.datamodel.StarDetail
import avi.sample.githubstars.network.ApiResult
import avi.sample.githubstars.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StarViewModel"

/**
 * ViewModel class for handling star (github user) details data.
 */
@HiltViewModel
class StarViewModel @Inject constructor(
    private val starDetailRepository: @JvmSuppressWildcards GitHubStarDetailRepo,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _starDetailResult = MutableLiveData<StarDetailApiResult>()
    val starDetailResult: LiveData<StarDetailApiResult> = _starDetailResult


    fun getStarDetails(user: String) {
        resetData()
        viewModelScope.launch(dispatcher.main) {
            handleResult(starDetailRepository.getDataFromGitHub(user))
        }
    }

    private fun resetData() {
        _starDetailResult.value = StarDetailApiResult(null, false, "")
    }

    private fun handleResult(result: ApiResult<StarDetail>) {
        when (result) {
            is ApiResult.Error -> {
                Log.e(TAG, "Error while getting github data: ${result.message}")
                _starDetailResult.value = StarDetailApiResult(null, true, result.message)
            }
            is ApiResult.Success -> {
                when (result.data) {
                    is StarDetail -> {
                        Log.d(TAG, "github data fetched.")
                        _starDetailResult.value = StarDetailApiResult(result.data, false, "")
                    }
                }
            }
        }
    }
 }