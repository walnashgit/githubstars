package avi.sample.githubstars.data

import androidx.paging.PagingData
import avi.sample.githubstars.datamodel.Stars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

import org.junit.Test

class FakeGitHubStarRepository: GitHubStarListRepo {

    val answer = flow<PagingData<Stars>> {
        emit(PagingData.empty())
    }

    override suspend fun getDataFromGitHub(query: String): Flow<PagingData<Stars>> {
        return answer
    }
}