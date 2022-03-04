package avi.sample.githubstars.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import avi.sample.githubstars.datamodel.Stars
import avi.sample.githubstars.network.*
import retrofit2.HttpException
import java.io.IOException

/**
 * Paging source to load github users list using pagination.
 */
class GitHubStarsPagingSource(
    private val githubApi: GitHubStarsApi,
    private val query: String = ""
) :
    PagingSource<Int, Stars>() {

    override fun getRefreshKey(state: PagingState<Int, Stars>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Stars> {
        val position = params.key ?: START_PAGE_INDEX
        val apiQuery = query.takeIf { it.isNotEmpty() } ?: QUERY

        return try {
            val response = githubApi.getGitHubStarsList(params.loadSize, position, apiQuery)
            val stars = response.stars
            val nextKey = if (stars.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = stars,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}