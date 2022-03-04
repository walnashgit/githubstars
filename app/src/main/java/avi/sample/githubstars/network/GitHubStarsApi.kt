package avi.sample.githubstars.network

import avi.sample.githubstars.datamodel.StarDetail
import avi.sample.githubstars.datamodel.StarList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Default query.
 */
const val QUERY = "followers:>1000"

/**
 * Default page size.
 */
const val PAGE_SIZE = 30

/**
 * Default page start index.
 */
const val START_PAGE_INDEX = 1

/**
 * GitHub API definition class.
 */
interface GitHubStarsApi: Api {

    @GET("search/users?sort=followers")
    suspend fun getGitHubStarsList(
        @Query("per_page") pageSize: Int,
        @Query("page") currentPage: Int,
        @Query("q") query: String
    ): StarList

    @GET("users/{name}")
    suspend fun getGitHubStarDetail(
        @Path("name") name: String
    ): Response<StarDetail>
}