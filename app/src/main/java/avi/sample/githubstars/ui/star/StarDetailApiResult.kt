package avi.sample.githubstars.ui.star

import avi.sample.githubstars.datamodel.StarDetail

/**
 * Wrapper data class to handle encapsulate the result of user details fetch and pass it on to
 * the UI.
 */
data class StarDetailApiResult(val data: StarDetail?, val error: Boolean, val message: String = "")
