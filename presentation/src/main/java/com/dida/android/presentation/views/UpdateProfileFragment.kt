package com.dida.android.presentation.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
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
import com.dida.android.util.uriToFile
import com.dida.common.ui.ImageBottomSheet
import com.dida.common.util.DidaIntent
import com.dida.update.profile.R
import com.dida.update.profile.UpdateProfileNavigationAction
import com.dida.update.profile.UpdateProfileViewModel
import com.dida.update.profile.databinding.FragmentUpdateProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment<FragmentUpdateProfileBinding, UpdateProfileViewModel>(R.layout.fragment_update_profile) {

    private val TAG = "UpdateProfileFragment"
    private val navController: NavController by lazy { findNavController() }
    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var cameraUri: Uri? = null

    // 요청하고자 하는 권한들
    private val permissionList = arrayOf(Manifest.permission.CAMERA)

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                toastMessage("권한 허용이 필요합니다.")
            }else{
                val dialog = ImageBottomSheet {
                    if(it) getGalleryImage()
                    else getCaptureImage()
                }
                dialog.show(childFragmentManager, TAG)
            }
        }
    }

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is UpdateProfileNavigationAction.NavigateToBack -> navController.popBackStack()
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
            editProfileImageBottomSheet()
        }
    }

    private fun editProfileImageBottomSheet() {
        requestMultiplePermission.launch(permissionList)

    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val isUpdateProfile = activityResult.data?.getBooleanExtra(DidaIntent.RESULT_KEY_UPDATE_PROFILE, false) ?: false
            if (!isUpdateProfile) {
                val intent = activityResult.data
                if (intent != null) {
                    val uri = intent.data
                    val file = uriToFile(uri!!, requireContext())
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
                    viewModel.selectProfileImage(uri, requestBody)
                }
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.let {
                createFile(it.data!!)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it) { cameraUri?.let { uri ->
                createFile(uri)
            } }
        }
    }

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)

    }

    private fun getCaptureImage() {
        cameraUri = createImageFile()
        cameraLauncher.launch(cameraUri)
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun createFile(uri: Uri) {
        val file = uriToFile(uri, requireContext())
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        viewModel.profileImageMultipartState.value = requestBody
        viewModel.selectProfileImage(uri, requestBody)
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
