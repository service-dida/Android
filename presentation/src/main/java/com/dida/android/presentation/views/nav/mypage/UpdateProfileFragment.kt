package com.dida.android.presentation.views.nav.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentUpdateProfileBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nickname.NicknameFragmentArgs
import com.dida.android.util.DidaIntent
import com.dida.android.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    val args: UpdateProfileFragmentArgs by navArgs()

    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initProfile(args.image,args.nickname,args.description)
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {

                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAfterBinding() {
        binding.descriptionEt.addTextChangedListener {
            binding.descriptionCheckTv.isVisible = it.toString().length>=30
        }
        binding.nicknameEt.setOnTouchListener(clearTextListener)
        binding.descriptionEt.setOnTouchListener(clearTextListener)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_confirm -> viewModel
                }
                true
            }
        }

        binding.profileCl.setOnClickListener {
            initRegisterForActivityResult()
        }
    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val isUpdateProfile = activityResult.data?.getBooleanExtra(DidaIntent.RESULT_KEY_UPDATE_PROFILE, false) ?: false
            if (isUpdateProfile) {
                val intent = activityResult.data
                if (intent != null) {
                    val uri = intent.data
                    val file = uriToFile(uri!!,requireContext())
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    val nicknamePart: MultipartBody.Part = MultipartBody.Part.createFormData("description", "테스트 설명")
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