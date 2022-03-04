package avi.sample.githubstars.base

interface BaseGitHubStarRepository<out T> {

    suspend fun getDataFromGitHub(query: String): T
}