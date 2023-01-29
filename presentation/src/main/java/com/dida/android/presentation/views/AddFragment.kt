package com.dida.android.presentation.views

import android.app.Activity
import android.content.Intent
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
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment() : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

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
        if(!viewModel.walletCheckState.value) viewModel.getWalletExists()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.walletExistsState.collectLatest {
                    // 지갑이 없는 경우 지갑 생성
                    if (it) {
                        if (!isSelected) {
                            PasswordDialog(6, "비밀번호 설정", "본인 확인 시 사용됩니다.") { success, _ ->
                                if (success) getImageToGallery()
                                else navController.popBackStack()
                            }.show(childFragmentManager, "AddFragment")
                        }
                    } else {
                        toastMessage("지갑을 생성해야 합니다!")
                        navigate(AddFragmentDirections.actionAddFragmentToEmailFragment())
                    }
                }
            }

            launch {
                viewModel.nftImageState.collectLatest {
                }
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
                        //TODO : 추후에 이미지 용량 체크도 해야합니다.
                        val uri = intent.data
                        viewModel.setNFTImage(uri)
                    }
                } else{
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
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.inflateMenu(R.menu.menu_add_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_next_step -> {
                        if(viewModel.titleLengthState.value == 0 || viewModel.descriptionLengthState.value == 0) {
                            isSelected = false
                            toastMessage("제목과 설명을 모두 입력해주세요.")
                        } else if(viewModel.nftImageState.value == "") {
                            isSelected = false
                            toastMessage("NFT에 사용할 이미지를 골라주세요.")
                        } else {
                            isSelected = true
                            //사진,제목, 설명 이동
                            val action = AddFragmentDirections.actionAddFragmentToAddPurposeFragment(
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
}