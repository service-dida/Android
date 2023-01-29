package com.dida.android.presentation.views

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.update.profile.R
import com.dida.android.util.uriToFile
import com.dida.common.util.DidaIntent
import com.dida.update.profile.UpdateProfileNavigationAction
import com.dida.update.profile.UpdateProfileViewModel
import com.dida.update.profile.databinding.FragmentUpdateProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment<FragmentUpdateProfileBinding, UpdateProfileViewModel>(R.layout.fragment_update_profile) {

    private val TAG = "UpdateProfileFragment"
    private val navController: NavController by lazy { findNavController() }
    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    override val layoutResourceId: Int
    get() = R.layout.fragment_update_profile

    override val viewModel: UpdateProfileViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRegisterForActivityResult()
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it){
                        is UpdateProfileNavigationAction.NavigateToBack -> navController.popBackStack()
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAfterBinding() {
        binding.nicknameEt.setOnTouchListener(clearTextListener)
        binding.descriptionEt.setOnTouchListener(clearTextListener)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_confirm -> viewModel.updateProfile()
                }
                true
            }
        }

        binding.profileCl.setOnClickListener {
           getImageToGallery()
        }
    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val isUpdateProfile = activityResult.data?.getBooleanExtra(DidaIntent.RESULT_KEY_UPDATE_PROFILE, false) ?: false
            if (!isUpdateProfile) {
                val intent = activityResult.data
                if (intent != null) {
                    val uri = intent.data
                    val file = uriToFile(uri!!,requireContext())
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
                    viewModel.selectProfileImage(uri,requestBody)
                }
            }
        }
    }

    private fun getImageToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        requestUpdateProfile.launch(intent)
    }

    val clearTextListener = object : OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
            val view = v as EditText
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(motionEvent.rawX >= view.right - view.compoundDrawables[2].getBounds().width()) {
                    when(v.id){
                        R.id.nickname_et ->{
                            viewModel.clearNickname()
                        }
                        R.id.description_et ->{
                            viewModel.clearDescription()
                        }
                    }
                    return true
                }
            }
            return false
        }
    }
}