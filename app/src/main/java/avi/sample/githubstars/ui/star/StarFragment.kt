package avi.sample.githubstars.ui.star

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import avi.sample.githubstars.R
import avi.sample.githubstars.databinding.StarDetailBinding
import avi.sample.githubstars.ui.main.MainViewModel
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StarFragment : Fragment() {

    companion object {
        fun newInstance() = StarFragment()
        const val USER = "user"
        const val AVATAR_URL = "url"
    }

    val viewModel by viewModels<StarViewModel>()
    private lateinit var binding : StarDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StarDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTransition()
        setupUI()
    }

    private fun setUpTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }
        postponeEnterTransition(1, TimeUnit.SECONDS)
    }

    private fun setupUI() {
        viewModel.starDetailResult.observe(viewLifecycleOwner) {
            binding.star = it
        }

        binding.avatarUrl = arguments?.getString(AVATAR_URL)
        val user = arguments?.getString(USER) ?: ""
        binding.user = user

        binding.starDetailStatus.starDetailRetry.setOnClickListener {
            getStarDetails(user)
        }
        getStarDetails(user)
    }

    private fun getStarDetails(user: String) {
        viewModel.getStarDetails(user)
    }
}