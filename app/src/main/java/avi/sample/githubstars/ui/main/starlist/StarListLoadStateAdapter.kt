package avi.sample.githubstars.ui.main.starlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import avi.sample.githubstars.R
import avi.sample.githubstars.databinding.LoadStateFooterBinding

/**
 * Adapter for showing LoadState as a footer in users list during pagination.
 */
class StarListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<StarListLoadStateAdapter.StarListLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: StarListLoadStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            (holder.binding as LoadStateFooterBinding).errorMsg.text = loadState.error.localizedMessage
        }
        (holder.binding as LoadStateFooterBinding).progressBar.isVisible = loadState is LoadState.Loading
        holder.binding.retryButton.isVisible = loadState is LoadState.Error
        holder.binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): StarListLoadStateViewHolder {
        return StarListLoadStateViewHolder(createBinding(parent))
    }

    private fun createBinding(parent: ViewGroup): LoadStateFooterBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.load_state_footer,
            parent,
            false
        )
    }

    inner class StarListLoadStateViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)  {
            init {
                (binding as LoadStateFooterBinding).retryButton.setOnClickListener {
                    retry()
                }
            }
        }
}