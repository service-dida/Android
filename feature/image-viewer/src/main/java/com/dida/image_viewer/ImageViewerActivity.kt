package com.dida.image_viewer

import androidx.activity.viewModels
import com.dida.common.base.BaseActivity
import com.dida.image_viewer.databinding.ActivityImageViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerActivity : BaseActivity<ActivityImageViewerBinding, ImageViewerViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_image_viewer

    override val viewModel: ImageViewerViewModel by viewModels()

    override fun initStartView() {}

    override fun initDataBinding() {}

    override fun initAfterBinding() {}
}
