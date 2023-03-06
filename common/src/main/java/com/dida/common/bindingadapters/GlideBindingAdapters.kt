package com.dida.common.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.R

object GlideBindingAdapters {

    @JvmStatic
    @BindingAdapter("image_from_url")
    fun bindImageFromURL(imageView: ImageView, imageURL: String?) {
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .override(1024, 1024)
                .transform(CenterCrop())
                .placeholder(R.mipmap.img_dida_logo_foreground)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_fullsize")
    fun bindImageFromURLFullSize(imageView: ImageView, imageURL: String?) {
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .placeholder(R.mipmap.img_dida_logo_foreground)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_rounded")
    fun bindImageFromURLRounded(imageView: ImageView, imageURL: String?) {
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("nft_img_rounded")
    fun bindNftImgRounded(imageView: ImageView, imageURL: String?) {
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_circle")
    fun bindImageFromURLCircle(imageView: ImageView, imageURL: String?) {
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(200))
                .into(imageView)
        }
    }
}
