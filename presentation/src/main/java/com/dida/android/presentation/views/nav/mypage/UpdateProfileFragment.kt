package com.dida.android.presentation.views.nav.mypage

import android.app.Activity
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.databinding.FragmentUpdateProfileBinding
import com.dida.android.presentation.adapter.home.RecentNftAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.DidaIntent
import com.dida.android.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment<FragmentUpdateProfileBinding, UpdateProfileViewModel>(R.layout.fragment_update_profile) {

    private val TAG = "UpdateProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_update_profile

    override val viewModel: UpdateProfileViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {

            }
        }
    }
    
    override fun initAfterBinding() {
        binding.descriptionEt.addTextChangedListener {
            binding.descriptionCheckTv.isVisible = it.toString().length>=30
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.inflateMenu(R.menu.menu_update_profile_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_confirm -> viewModel
                }
                true
            }
        }
    }
}