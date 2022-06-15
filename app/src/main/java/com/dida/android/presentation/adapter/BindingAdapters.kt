package com.dida.android.presentation.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("image_from_url")
    fun bindImageFromURL(imageView: ImageView, imageURL: String?) {
        Log.d(TAG, "bindImageFromURL: ${imageURL}")
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .into(imageView)
        }
    }
}