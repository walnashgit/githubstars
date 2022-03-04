package avi.sample.githubstars.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

/**
 * Load image in an [imageview] based on the [url] passed using Glide.
 */
@BindingAdapter("loadImage")
fun loadImage(imageview: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageview).load(url).into(imageview)
    }
}

/**
 * Load image in an [imageview] using Glide, based on the [url] passed and
 * update status to [listener].
 */
@BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
    Glide.with(imageView).load(url).listener(listener).into(imageView)
}