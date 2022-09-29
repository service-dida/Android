package com.dida.android.presentation.views.nav.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentAddBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment() : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel: AddViewModel by viewModels()

    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var isSelected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이메일에서 인증 완료후 돌아 왔을 때
        setFragmentResultListener("walletCheck") { _, bundle ->
            val result = bundle.getBoolean("hasWallet")
            if(result) { getImageToGallery() }
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
        textLengthCheckListener(binding.titleEditText)
        textLengthCheckListener(binding.descriptionEditText)

        // User의 지갑이 있는지 체크
        viewModel.getWalletExists()
        showLoadingDialog()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.walletExistsState.collect {
                    dismissLoadingDialog()
                    // 지갑이 없는 경우 지갑 생성
                    if (!it) {
                        toastMessage("지갑을 생성해야 합니다!")
                        navController.navigate(R.id.action_addFragment_to_emailFragment)
                    } else {
                        if(!isSelected){
                            val passwordDialog = PasswordDialog(true) { password ->
                                viewModel.checkPassword(password)
                                showLoadingDialog()
                            }
                            passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
                        }
                    }
                }
            }

            launch {
                viewModel.checkPasswordState.collect {
                    dismissLoadingDialog()
                    if(it) { getImageToGallery() }
                    else {
                        toastMessage("비밀번호가 틀렸습니다.")
                        val passwordDialog = PasswordDialog(true) { password ->
                            viewModel.checkPassword(password)
                            showLoadingDialog()
                        }
                        passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
                    }
                }
            }

            launch {
                viewModel.nftImageState.collect {

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
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener {
                navController.popBackStack()
            }
            this.inflateMenu(R.menu.menu_add_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_next_step -> {
                        if(binding.titleEditText.length()==0 || binding.descriptionEditText.length()==0){
                            toastMessage("제목과 설명을 모두 입력해주세요.")
                        }else{
                            isSelected = true
                            //사진,제목, 설명 이동
                            val action = AddFragmentDirections.actionAddFragmentToAddPurposeFragment(
                                viewModel.nftImageState.value.toString(),
                                binding.titleEditText.text.toString(),
                                binding.descriptionEditText.text.toString())
                            navigate(action)
                        }
                    }
                }
                true
            }
        }
    }
    
    private fun textLengthCheckListener(editText : EditText){
        editText.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val length = editText.length()
                if(editText==binding.titleEditText){
                    viewModel.setTitleLength(length)
                } else if(editText==binding.descriptionEditText){
                    viewModel.setDescriptionLength(length)
                }
            }
        })
    }
}