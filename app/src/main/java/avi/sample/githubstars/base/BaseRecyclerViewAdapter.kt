package avi.sample.githubstars.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic RecyclerView adapter using DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <VH> The type of the View Holder
 */
abstract class BaseRecyclerViewAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(diffCallback)