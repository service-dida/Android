package com.dida.android.presentation.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.add.R
import com.dida.add.databinding.FragmentAddBinding
import com.dida.add.databinding.FragmentCreateNftBinding
import com.dida.add.main.AddViewModel
import com.dida.add.main.CreateNftViewModel
import com.dida.common.customview.addOnFocusListener
import com.dida.common.util.repeatOnCreated
import com.dida.password.PasswordDialog
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

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun initStartView() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initNextButton()
        initRegisterForActivityResult()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToGallery.collectLatest {
                getImageToGallery()
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        initEditText()
    }

    private fun initRegisterForActivityResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    if (intent != null) {
                        val uri = intent.data
                        if (checkImageSize(uri!!)) {
                            viewModel.setNFTImage(uri)
                        } else {
                            showToastMessage("사진의 용량은 10MB를 넘길 수 없습니다.")
                            getImageToGallery()
                        }
                    }
                } else {
                    navController.popBackStack()
                }
            }
    }

    private fun getImageToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
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
