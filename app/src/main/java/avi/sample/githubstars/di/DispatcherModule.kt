package avi.sample.githubstars.di

import avi.sample.githubstars.util.DefaultDispatchers
import avi.sample.githubstars.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DispatcherModule {

    @Binds
    abstract fun bindDispatcher(defaultDispatchers: DefaultDispatchers): DispatcherProvider
}