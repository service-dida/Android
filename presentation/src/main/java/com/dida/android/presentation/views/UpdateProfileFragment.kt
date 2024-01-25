package com.dida.android.presentation.views

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.util.uriToFile
import com.dida.common.ui.ImageBottomSheet
import com.dida.common.widget.DefaultSnackBar
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

    private val galleryPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
        imageUri?.let {
            createFile(it)
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            createFile(it)
        }
    }

    private val cameraLauncher: ActivityResultLauncher<Uri> = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            cameraUri?.let { uri ->
                createFile(uri)
            }
        }
    }

    private var cameraUri: Uri? = null

    private val askMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.values.all { it == true }.let { allPermissionGranted ->
            if (allPermissionGranted) {
                showImageFromBottomSheet()
            } else {
                showToastMessage("권한을 허용해 주세요.")
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
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is UpdateProfileNavigationAction.NavigateToBack -> {
                        showMessageSnackBar(getString(com.dida.android.R.string.update_profile_message))
                        navController.popBackStack()
                    }
                    is UpdateProfileNavigationAction.NavigateToUpdateProfileImage -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            showImageFromBottomSheet()
                        } else {
                            askMultiplePermissions.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))
                        }
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_confirm -> viewModel.updateProfile()
                }
                true
            }
        }
    }

    private fun showImageFromBottomSheet() {
        val dialog = ImageBottomSheet { getGallery ->
            if (getGallery) launchGallery()
            else launchCamera()
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun launchGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            galleryPickerLauncher.launch(PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build())
        } else {
            galleryLauncher.launch("image/*")
        }
    }

    private fun launchCamera() {
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

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .hasBottomMargin(false)
            .build()
    }
}
