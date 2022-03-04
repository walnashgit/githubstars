package avi.sample.githubstars.ui.main.starlist

import androidx.recyclerview.widget.DiffUtil
import avi.sample.githubstars.datamodel.Stars

/**
 * Diff callback for checking difference between two items in user list.
 */
class StarListDiffCallback: DiffUtil.ItemCallback<Stars>() {

    override fun areItemsTheSame(oldItem: Stars, newItem: Stars): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: Stars, newItem: Stars): Boolean {
        return (oldItem.login == newItem.login || oldItem.id == newItem.id)
    }
}