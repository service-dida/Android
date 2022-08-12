package com.dida.android.presentation.views.nav.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentAddBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.mypage.MyPageFragmentDirections
import com.dida.data.DataApplication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel: AddViewModel by viewModels()

    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        Email Fragment 에서 완료를 했을 경우에는 현재화면에서 NFT 생성
        아닐 경우에는 Toast 메세지를 띄우고 뒤로 가기
        * */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("WalletCheck")
            ?.observe(viewLifecycleOwner) {
<<<<<<< HEAD
                if(!it) {
                    Toast.makeText(requireContext(), "지갑을 생성해야 NFT를 만들 수 있습니다.", Toast.LENGTH_SHORT).show()
=======
                if (!it) {
                    Toast.makeText(requireContext(), "지갑을 생성해야 NFT를 만들 수 있습니다.", Toast.LENGTH_SHORT)
>>>>>>> feature/NFTAddUI
                    navController.popBackStack()
                }
            }
    }

    override fun initStartView() {
        binding.vm = viewModel
        // User의 지갑이 있는지 체크
        initRegisterForActivityResult()
        viewModel.getWalletExists()
    }

    override fun initDataBinding() {
        viewModel.walletExistsLiveData.observe(this) {
            // 지갑이 없는 경우 지갑 생성
            if (!it) {
                navController.navigate(R.id.action_addFragment_to_emailFragment)
            } else {
                getImageToGallery()
            }
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireContext(), "네트워크 상태가 좋지 않습니다!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    override fun initAfterBinding() {
        viewModel.nftImageLiveData.observe(viewLifecycleOwner) {
            initToolbar()
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
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
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
                    //TODO : 다음단계로 넘어가기
                }
            }
            true
        }
    }
    
    private fun textLengthCheck(){
        //TODO : 제목하고 설명 글자색 체크하기
    }
}