package com.dida.android.presentation.views.nav.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentAddBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.password.PasswordDialog
import com.dida.android.util.AppLog
import dagger.hilt.android.AndroidEntryPoint

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
        /*
        Email Fragment 에서 완료를 했을 경우에는 현재화면에서 NFT 생성
        아닐 경우에는 Toast 메세지를 띄우고 뒤로 가기
        * */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("WalletCheck")?.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(requireContext(), "지갑을 생성해야 NFT를 만들 수 있습니다.", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            else {
                getImageToGallery()
            }
        }
    }

    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()
        initRegisterForActivityResult()
        textLengthCheckListener(binding.titleEditText)
        textLengthCheckListener(binding.descriptionEditText)

        // User의 지갑이 있는지 체크
        //viewModel.getWalletExists()
        //showLoadingDialog()
        getImageToGallery()

    }

    override fun initDataBinding() {
        viewModel.walletExistsLiveData.observe(this) {
            dismissLoadingDialog()
            // 지갑이 없는 경우 지갑 생성
            if (!it) {
                Toast.makeText(requireContext(), "지갑을 생성해야 합니다!", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_addFragment_to_emailFragment)
            }
            else {
                if(!isSelected){
                    val passwordDialog = PasswordDialog(true) { password ->
                        viewModel.checkPassword(password)
                        showLoadingDialog()
                    }
                    passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
                }
            }
        }

        viewModel.checkPasswordLiveData.observe(this) {
            dismissLoadingDialog()
            if(it) {
                getImageToGallery()
            }
            else {
                Toast.makeText(requireContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                val passwordDialog = PasswordDialog(true) { password ->
                    viewModel.checkPassword(password)
                    showLoadingDialog()
                }
                passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
            }
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireContext(), "네트워크 상태가 좋지 않습니다!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    override fun initAfterBinding() {
        viewModel.nftImageLiveData.observe(viewLifecycleOwner) {

        }
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
                }else{
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
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        binding.toolbar.inflateMenu(R.menu.menu_add_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_next_step -> {
                    if(binding.titleEditText.length()==0 || binding.descriptionEditText.length()==0){
                        Toast.makeText(requireContext(),"제목과 설명을 모두 입력해주세요.",Toast.LENGTH_SHORT).show()
                    }else{
                        isSelected = true
                        //사진,제목, 설명 이동
                        val action = AddFragmentDirections.actionAddFragmentToAddPurposeFragment(
                            viewModel.nftImageLiveData.value.toString(),
                            binding.titleEditText.text.toString(),
                            binding.descriptionEditText.text.toString())
                        navController.navigate(action)
                    }
                }
            }
            true
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
                }else if(editText==binding.descriptionEditText){
                    viewModel.setDescriptionLength(length)
                }
            }
        })
    }
}