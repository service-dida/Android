package com.dida.update.profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("updateProfileImage")
fun ImageView.bindUpdateProfileImage(state : MutableStateFlow<String>) {
    if (state.value.isNotBlank()) {
        Glide.with(context)
            .load(state.value)
            .placeholder(com.dida.common.R.mipmap.img_dida_logo_foreground)
            .error(com.dida.common.R.mipmap.img_dida_logo_foreground)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    } else {
        Glide.with(context)
            .load(com.dida.common.R.mipmap.img_dida_logo_foreground)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}


@BindingAdapter("updateProfileToolbar")
fun Toolbar.bindToolbar(state : StateFlow<Boolean>) {
    this.menu.clear()
    if (state.value) {
        this.inflateMenu(R.menu.menu_enable_update_profile_toolbar)
    } else {
        this.inflateMenu(R.menu.menu_unable_update_profile_toolbar)
    }
}

@BindingAdapter("descriptionVisible")
fun TextView.bindDescriptionVisible(isInVisible: Boolean) {
    val view = this
    if(isInVisible) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
    }
}
