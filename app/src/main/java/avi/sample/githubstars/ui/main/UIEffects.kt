package avi.sample.githubstars.ui.main

import android.widget.ImageView
import avi.sample.githubstars.base.Effect
import avi.sample.githubstars.datamodel.Stars

/**
 * Effects for the UI to handle.
 */
sealed class UIEffects: Effect {
    /**
     * UI effect for refreshing the data.
     */
    object Refresh: UIEffects()

    /**
     * UI effect for retry last fetch.
     */
    object Retry: UIEffects()

    /**
     * UI effect for handling click on a user item.
     */
    data class OpenStarDetailFragment(val imageView: ImageView, val star: Stars): UIEffects()
}