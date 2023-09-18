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
        imageURL?.let {
            if (it.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .transform(CenterCrop())
                    .placeholder(R.mipmap.img_dida_logo_foreground)
                    .error(R.mipmap.img_dida_logo_foreground)
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop())
                    .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_fullsize")
    fun bindImageFromURLFullSize(imageView: ImageView, imageURL: String?) {
        imageURL?.let {
            if (it.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .placeholder(R.mipmap.img_dida_logo_foreground)
                    .error(R.mipmap.img_dida_logo_foreground)
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(R.mipmap.img_dida_logo_foreground)
                    .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_rounded")
    fun bindImageFromURLRounded(imageView: ImageView, imageURL: String?) {
        imageURL?.let {
            if (it.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .placeholder(R.mipmap.img_dida_logo_foreground)
                    .error(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(50))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(50))
                    .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("nft_img_rounded")
    fun bindNftImgRounded(imageView: ImageView, imageURL: String?) {
        imageURL?.let {
            if (imageURL.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .placeholder(R.mipmap.img_dida_logo_foreground)
                    .error(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_circle")
    fun bindImageFromURLCircle(imageView: ImageView, imageURL: String?) {
        if (imageURL == null) {
            Glide.with(imageView.context)
                .load(R.mipmap.img_dida_logo_foreground)
                .transform(CenterCrop(), RoundedCorners(200))
                .into(imageView)
        } else {
            if (imageURL.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(imageURL)
                    .placeholder(R.mipmap.img_dida_logo_foreground)
                    .error(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(200))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(R.mipmap.img_dida_logo_foreground)
                    .transform(CenterCrop(), RoundedCorners(200))
                    .into(imageView)
            }
        }
    }
}
