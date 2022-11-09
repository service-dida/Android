package com.dida.android.presentation.views.nav.mypage

import android.app.Activity
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.presentation.adapter.home.RecentNftAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
        initRegisterForActivityResult()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is MypageNavigationAction.NavigateToHome -> {
                        toastMessage("로그아웃 하였습니다.")
                        navController.popBackStack()
                    }
                    is MypageNavigationAction.NavigateToEmail -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment())
                    is MypageNavigationAction.NavigateToWallet -> navigate(MyPageFragmentDirections.actionMyPageFragmentToWalletFragment())
                    //TODO : 상세페이지 CardId 값 수정하기
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment(it.cardId))
                }
            }
        }
    }
    
    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMypage()
    }

    private fun initMyPage() {
        initToolbar()
        initSpinner()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvUserNft.apply {
            adapter = RecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.inflateMenu(R.menu.menu_mypage_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_wallet -> viewModel.onWalletClicked()
                    R.id.action_setting -> viewModel.onLogoutClicked()
                    R.id.action_profileImg -> getImageToGallery()
                    R.id.action_temporary_password -> viewModel.tempPassword()
                    R.id.action_change_password -> viewModel.changePassword("203057","000000")
                }
                true
            }
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mypage_spinner_list,
            R.layout.holder_mypage_nft_type_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }


    private fun initRegisterForActivityResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    if (intent != null) {
                        val uri = intent.data
                        val file = uriToFile(uri!!,requireContext())
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
                        
                        //TODO : 이후에 설명도 입력한걸로 넣기
                        val nicknamePart: MultipartBody.Part = MultipartBody.Part.createFormData("description", "테스트 설명")
                        viewModel.updateProfile(nicknamePart , requestBody)
                    }
                }
            }
    }

    private fun getImageToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
}