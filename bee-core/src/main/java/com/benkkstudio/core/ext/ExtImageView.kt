package com.benkkstudio.core.ext

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.benkkstudio.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ImageView.load(
    image: String,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
    onError: (() -> Unit)? = null,
    onSuccess: ((bitmap: Bitmap) -> Unit)? = null,
) {
    Glide.with(this)
        .asBitmap()
        .load(image)
        .placeholder(placeholder)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    onError?.invoke()
                }
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    onSuccess?.invoke(resource)
                    setImageBitmap(resource)
                }
                return true
            }

        })
        .submit()
}

fun ImageView.load(
    image: String,
    onError: () -> Unit,
    onSuccess: () -> Unit,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
) {
    Glide.with(this)
        .asBitmap()
        .load(image)
        .placeholder(placeholder)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    onError.invoke()
                }
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    onSuccess.invoke()
                    setImageBitmap(resource)
                }
                return true
            }

        })
        .submit()
}

fun ImageView.load(
    image: String,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
    onFinish: (() -> Unit)? = null
) {
    Glide.with(this)
        .asBitmap()
        .load(image)
        .placeholder(placeholder)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                CoroutineScope(Dispatchers.Main).launch {
                    onFinish?.invoke()
                    setImageBitmap(resource)
                }
                return true
            }

        })
        .submit()
}

fun ImageView.load(
    image: String,
    blur: Boolean = false,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
) {
    if (blur) {
        Glide.with(this)
            .asBitmap()
            .load(image)
            .apply(RequestOptions.bitmapTransform(jp.wasabeef.glide.transformations.BlurTransformation(25, 3)))
            .placeholder(placeholder)
            .into(this)
        return
    }
    Glide.with(this)
        .asBitmap()
        .load(image)
        .placeholder(placeholder)
        .into(this)
}
