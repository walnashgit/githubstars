package avi.sample.githubstars.di

import avi.sample.githubstars.data.GitHubStarDetailRepo
import avi.sample.githubstars.data.GitHubStarDetailRepository
import avi.sample.githubstars.data.GitHubStarRepository
import avi.sample.githubstars.data.GitHubStarListRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GitHubStarRepositoryModule {

    @Binds
    abstract fun bindGitHubStarRepository(gitHubStarRepository: GitHubStarRepository): GitHubStarListRepo

    @Binds
    abstract fun bindGitHubStarDetailRepository(gitHubStarDetail: GitHubStarDetailRepository): GitHubStarDetailRepo

}