package avi.sample.githubstars.datamodel


import com.google.gson.annotations.SerializedName

data class StarList(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val stars: List<Stars>,
    @SerializedName("total_count")
    val totalCount: Int
)