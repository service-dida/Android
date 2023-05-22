package com.dida.android.presentation.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.add.R
import com.dida.add.databinding.FragmentAddBinding
import com.dida.add.main.AddViewModel
import com.dida.common.util.repeatOnStarted
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel: AddViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var isSelected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이메일에서 인증 완료후 돌아 왔을 때
        setFragmentResultListener("walletCheck") { _, bundle ->
            val result = bundle.getBoolean("hasWallet")
            if (result) {
                viewModel.createWallet()
                getImageToGallery()
            }
        }

    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initRegisterForActivityResult()
    }

    override fun onResume() {
        super.onResume()
        // User의 지갑이 있는지 체크
        if (!viewModel.walletCheckState.value) viewModel.getWalletExists()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.walletExistsState.collectLatest {
                // 지갑이 없는 경우 지갑 생성
                if (it) {
                    if (!isSelected) {
                        PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, msg ->
                            if(success){
                                getImageToGallery()
                            }else{
                                if(msg=="reset"){
                                    navigate(AddFragmentDirections.actionAddFragmentToSettingFragment())
                                }else{
                                    navController.popBackStack()
                                }
                            }
                        }.show(childFragmentManager, "AddFragment")
                    }
                } else {
                    showToastMessage("지갑을 생성해야 합니다!")
                    navigate(AddFragmentDirections.actionAddFragmentToEmailFragment(RequestEmailType.MAKE_WALLET))
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.nftImageState.collectLatest {
            }
        }
    }

    override fun initAfterBinding() {
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
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.inflateMenu(R.menu.menu_add_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_next_step -> {
                        if (viewModel.titleLengthState.value == 0 || viewModel.descriptionLengthState.value == 0) {
                            isSelected = false
                            showToastMessage("제목과 설명을 모두 입력해주세요.")
                        } else if (viewModel.nftImageState.value == "") {
                            isSelected = false
                            showToastMessage("NFT에 사용할 이미지를 골라주세요.")
                        } else {
                            isSelected = true
                            //사진,제목, 설명 이동
                            val action =
                                AddFragmentDirections.actionAddFragmentToAddPurposeFragment(
                                    viewModel.nftImageState.value,
                                    viewModel.titleTextState.value,
                                    viewModel.descriptionTextState.value
                                )
                            navigate(action)
                        }
                    }
                }
                true
            }
        }
    }

    fun checkImageSize(uri: Uri): Boolean {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val bytes = inputStream?.buffered()?.use { it.readBytes() }
        val sizeInMb = bytes?.size?.toDouble()?.div(1024)?.div(1024)

        if (sizeInMb != null && sizeInMb > 10) {
            // 용량이 10MB를 초과하면 처리할 코드 작성
            return false
        } else {
            // 용량이 10MB를 초과하지 않으면 처리할 코드 작성
            return true
        }
    }
}
