package avi.sample.githubstars.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.transition.TransitionInflater
import avi.sample.githubstars.R
import avi.sample.githubstars.databinding.MainFragmentBinding
import avi.sample.githubstars.datamodel.Stars
import avi.sample.githubstars.ui.star.StarFragment
import avi.sample.githubstars.ui.main.starlist.StarListDataAdapter
import avi.sample.githubstars.ui.main.starlist.StarListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var starListAdapter: StarListDataAdapter

    val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observerUIState()
        observeUIEffects()
        observeLoadState()
    }

    private fun setupUI() {

        starListAdapter = StarListDataAdapter { imageView, user ->
            viewModel.setEvent(UIEvent.UserClicked(imageView, user))
        }

        // Set list adapter with footer for showing load state.
        binding.starsRecyclerView.adapter =
            starListAdapter.withLoadStateFooter(StarListLoadStateAdapter {
                viewModel.setEvent(UIEvent.Retry)
            })

        binding.pullToRefresh.setOnRefreshListener {
            binding.pullToRefresh.isRefreshing = false
            starListAdapter.refresh()
        }

        postponeEnterTransition()
        binding.starsRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun launchStarFragment(imageView: ImageView, user: Stars) {
        val starFragment = StarFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(StarFragment.USER, user.login)
        bundle.putString(StarFragment.AVATAR_URL, user.avatarUrl)
        starFragment.arguments = bundle
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(imageView, user.login)
            .replace(R.id.container, starFragment)
            .addToBackStack("star")
            .commit()
    }

    private fun observerUIState() {
        viewLifecycleOwner.lifecycleScope .launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        AppState.AppLaunch -> {
                            viewModel.setEvent(UIEvent.FetchStarList())
                        }
                        is AppState.StarListFetchResult -> {
                            handleData(it.apiResult)
                        }
                    }
                }
            }
        }
    }

    private fun observeUIEffects() {
        viewLifecycleOwner.lifecycleScope .launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it){
                        is UIEffects.OpenStarDetailFragment -> {
                            launchStarFragment(it.imageView, it.star)
                        }
                        UIEffects.Refresh -> {
                            starListAdapter.refresh()
                        }
                        UIEffects.Retry -> {
                            starListAdapter.retry()
                        }
                    }
                }
            }
        }
    }

    /**
     * Observes the LoadState of paging data and sets various UI element accordingly.
     */
    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                starListAdapter.loadStateFlow.collect { loadState ->
                    val isLoading = loadState.source.refresh is LoadState.Loading

                    binding.errorTxt.isVisible = loadState.source.refresh is LoadState.Error
                    binding.retryMsg.isVisible = loadState.source.refresh is LoadState.Error
                    if (loadState.source.refresh is LoadState.Error) {
                        binding.errorTxt.text =
                            (loadState.source.refresh as LoadState.Error).error.localizedMessage
                    }
                    binding.starsRecyclerView.isVisible = !isLoading
                    binding.loading.isVisible = isLoading
                }
            }
        }
    }

    private fun handleData(result: Flow<PagingData<Stars>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collectLatest {
                    starListAdapter.submitData(it)
                }
            }
        }
    }
}