package avi.sample.githubstars.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import avi.sample.githubstars.base.BaseGitHubStarRepository
import avi.sample.githubstars.datamodel.Stars
import avi.sample.githubstars.network.GitHubStarsApi
import avi.sample.githubstars.network.PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GitHubStarListRepo = BaseGitHubStarRepository<@JvmSuppressWildcards Flow<PagingData<Stars>>>

@ViewModelScoped
class GitHubStarRepository @Inject constructor(private val gitHubStarsApi: GitHubStarsApi) :
    GitHubStarListRepo {

    /**
     * Get list of users as [PagingData] from github based on the [query] passed.
     */
    override suspend fun getDataFromGitHub(query: String): Flow<PagingData<Stars>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GitHubStarsPagingSource(gitHubStarsApi, query)
            }
        ).flow
    }
}