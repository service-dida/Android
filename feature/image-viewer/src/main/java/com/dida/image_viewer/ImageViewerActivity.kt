package com.dida.image_viewer

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dida.common.base.BaseActivity
import com.dida.image_viewer.databinding.ActivityImageViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerActivity : BaseActivity<ActivityImageViewerBinding, ImageViewerViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_image_viewer

    override val viewModel: ImageViewerViewModel by viewModels()

    override fun initStartView() {
        initToolbar()
        initImage()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { finish() }
        }
    }

    private fun initImage() {
        intent?.getStringExtra(KEY_IMAGE_URLS)?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .error(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .into(binding.imageView)
        } ?: finish()

        intent?.getStringExtra(KEY_IMAGE_TITLES)?.let {
            binding.imageTitle.text = it
        } ?: finish()

        intent?.getStringExtra(KEY_IMAGE_DESCRIPTION)?.let {
            binding.imageDescription.text = it
        } ?: finish()
    }

    companion object {
        const val KEY_IMAGE_URLS = "KEY_IMAGE_URLS"
        const val KEY_IMAGE_TITLES = "KEY_IMAGE_TITLES"
        const val KEY_IMAGE_DESCRIPTION = "KEY_IMAGE_DESCRIPTION"

        fun starterIntent(context: Context, imageUrl: String?, imageTitle: String?, imageDescription: String?): Intent =
            Intent(context, ImageViewerActivity::class.java)
                .putExtra(KEY_IMAGE_URLS, imageUrl)
                .putExtra(KEY_IMAGE_TITLES, imageTitle)
                .putExtra(KEY_IMAGE_DESCRIPTION, imageDescription)

    }
}
