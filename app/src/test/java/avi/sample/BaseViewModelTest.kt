package avi.sample

import avi.sample.githubstars.TestDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {

    protected val testDispatcher by lazy {
        TestDispatcher()
    }
}