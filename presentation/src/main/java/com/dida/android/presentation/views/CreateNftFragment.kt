package com.dida.android.presentation.views

import android.net.Uri
import android.os.Build
import android.util.TypedValue
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.add.R
import com.dida.add.databinding.FragmentCreateNftBinding
import com.dida.add.main.CreateNftViewModel
import com.dida.common.customview.addOnFocusListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateNftFragment : BaseFragment<FragmentCreateNftBinding, CreateNftViewModel>(R.layout.fragment_create_nft) {

    private val TAG = "CreateNftFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_create_nft

    override val viewModel: CreateNftViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }
    private val args: CreateNftFragmentArgs by navArgs()

    private val galleryPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
        onCheckSelectImage(imageUri)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        onCheckSelectImage(imageUri)
    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initNextButton()
        initImage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToGallery.collectLatest {
                navigateToGallery()
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        initEditText()
    }

    private fun initImage() {
        if (args.imgUrl == null) {
            navigateToGallery()
        } else {
            viewModel.setNFTImage(Uri.parse(args.imgUrl))
        }
    }

    private fun onCheckSelectImage(imageUri: Uri?) {
        if (imageUri == null) {
            navController.popBackStack()
        } else {
            if (checkImageSize(imageUri)) {
                viewModel.setNFTImage(imageUri)
            } else {
                showToastMessage("사진의 용량은 10MB를 넘길 수 없습니다.")
                navigateToGallery()
            }
        }
    }

    private fun navigateToGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launchGalleryPicker()
        } else {
            launchGalleryPicker()
        }
    }

    private fun launchGalleryPicker() {
        if (Build.VERSION.SDK_INT >= 33) {
            galleryPickerLauncher.launch(PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build())
        } else {
            galleryLauncher.launch("image/*")
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initNextButton() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToAddPurpose.collectLatest {
                if (!viewModel.hasNextState.value) {
                    showToastMessage(getString(R.string.not_yet_title_and_description))
                } else if (viewModel.nftImageState.value == "") {
                    showToastMessage(getString(R.string.not_yet_image))
                } else {
                    val action =
                        CreateNftFragmentDirections.actionCreateNftFragmentToAddPurposeFragment(
                            viewModel.nftImageState.value,
                            viewModel.titleTextState.value,
                            viewModel.descriptionTextState.value
                        )
                    navigate(action)
                }
            }
        }
    }

    private fun checkImageSize(uri: Uri): Boolean {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val bytes = inputStream?.buffered()?.use { it.readBytes() }
        val sizeInMb = bytes?.size?.toDouble()?.div(1024)?.div(1024)

        return !(sizeInMb != null && sizeInMb > 10)
    }

    private fun initEditText() {
        binding.titleEditText.addOnFocusListener(focus = { handleEditTextFocus(it) })
        binding.descriptionEditText.addOnFocusListener(focus = { handleEditTextFocus(it) })
    }

    private fun handleEditTextFocus(hasFocus: Boolean): Unit {
        val contractDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            if (hasFocus) 122f else 162f,
            resources.displayMetrics
        )
        binding.nftAddImageView.layoutParams.apply {
            width = contractDp.toInt()
            height = contractDp.toInt()
            binding.nftAddImageView.layoutParams = this
        }

    }
}
