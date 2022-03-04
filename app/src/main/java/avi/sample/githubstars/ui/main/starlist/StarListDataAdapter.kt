package avi.sample.githubstars.ui.main.starlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import avi.sample.githubstars.R
import avi.sample.githubstars.base.BaseRecyclerViewAdapter
import avi.sample.githubstars.datamodel.Stars
import avi.sample.githubstars.databinding.StarsBinding

/**
 * Adapter for recycler view for showing users list using pagination.
 */
class StarListDataAdapter (private val onItemClicked: (imageView: ImageView, star: Stars) -> Unit) :
    BaseRecyclerViewAdapter<Stars, StarListDataAdapter.StarListDataViewHolder>(
        diffCallback = StarListDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarListDataViewHolder {
        return StarListDataViewHolder(createBinding(parent))
    }

    override fun onBindViewHolder(holder: StarListDataViewHolder, position: Int) {
        (holder.binding as StarsBinding).star = getItem(position)
        holder.binding.executePendingBindings()
        // Set transition name for shared element transition.
        holder.binding.avatar.transitionName = holder.binding.star?.login ?: ""
    }

    private fun createBinding(parent: ViewGroup): StarsBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.stars,
            parent,
            false
        )
    }

    inner class StarListDataViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    (binding as StarsBinding).star?.let { star ->
                        onItemClicked(binding.avatar, star)
                    }
                }
            }
        }
}