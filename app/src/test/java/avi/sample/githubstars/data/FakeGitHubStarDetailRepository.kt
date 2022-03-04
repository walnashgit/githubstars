package avi.sample.githubstars.data

import avi.sample.githubstars.datamodel.StarDetail
import avi.sample.githubstars.network.ApiResult
import org.junit.Assert.*

const val ERROR_MSG = "Returning error."
class FakeGitHubStarDetailRepository : GitHubStarDetailRepo {

    var returnError = false

    override suspend fun getDataFromGitHub(query: String): ApiResult<StarDetail> {
        if (returnError) {
            return ApiResult.Error(null, ERROR_MSG)
        }
        return ApiResult.Success(null)
    }
}