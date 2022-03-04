package avi.sample.githubstars.ui.main

import android.widget.ImageView
import avi.sample.githubstars.base.Event
import avi.sample.githubstars.datamodel.Stars

/**
 * Events for this app.
 */
sealed class UIEvent: Event {
    /**
     * Event to fetch list of stars (github users).
     */
    data class FetchStarList(val query: String = ""): UIEvent()

    /**
     * Event when retry is clicked at the list footer.
     */
    object Retry: UIEvent()

    /**
     * Event when it's swiped down to refresh.
     */
    object Refresh: UIEvent()

    /**
     * Event when a user item is clicked.
     */
    data class UserClicked(val imageView: ImageView, val star: Stars): UIEvent()
}
