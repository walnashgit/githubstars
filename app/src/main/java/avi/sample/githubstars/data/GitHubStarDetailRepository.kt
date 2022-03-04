package avi.sample.githubstars.data

import avi.sample.githubstars.base.BaseGitHubStarRepository
import avi.sample.githubstars.datamodel.StarDetail
import avi.sample.githubstars.network.ApiResult
import avi.sample.githubstars.network.GitHubStarsApi
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

typealias GitHubStarDetailRepo = BaseGitHubStarRepository<@JvmSuppressWildcards ApiResult<StarDetail>>

/**
 * Repository for fetching a user's details data from github using [gitHubStarsApi].
 */
@ViewModelScoped
class GitHubStarDetailRepository @Inject constructor(private val gitHubStarsApi: GitHubStarsApi): GitHubStarDetailRepo {

    /**
     * Get data from github for the user passed as a [query].
     */
    override suspend fun getDataFromGitHub(query: String): ApiResult<StarDetail> {
        try {
            val response = gitHubStarsApi.getGitHubStarDetail(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    return ApiResult.Success(it)
                }
            }
            return getErrorResult("${response.message()} : ${response.code()}")
        } catch (e: Exception) {
            return getErrorResult(e.message ?: e.toString())
        }
    }

    private fun <T>getErrorResult(message: String): ApiResult<T> {
        return ApiResult.Error(message = "Call to API failed: $message")
    }
}