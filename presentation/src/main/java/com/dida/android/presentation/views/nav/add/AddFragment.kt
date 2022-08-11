package com.dida.android.presentation.views.nav.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentAddBinding
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel : AddViewModel by viewModels()

    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        Email Fragment 에서 완료를 했을 경우에는 현재화면에서 NFT 생성
        아닐 경우에는 Toast 메세지를 띄우고 뒤로 가기
        * */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("WalletCheck")
            ?.observe(viewLifecycleOwner) {
                if(!it) {
                    Toast.makeText(requireContext(), "지갑을 생성해야 NFT를 만들 수 있습니다.", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
    }

    override fun initStartView() {
        // User의 지갑이 있는지 체크
        viewModel.getWalletExists()
    }

    override fun initDataBinding() {
        viewModel.walletExistsLiveData.observe(this) {
            // 지갑이 없는 경우 지갑 생성
            if(!it) {
                navController.navigate(R.id.action_addFragment_to_emailFragment)
            }
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireContext(), "네트워크 상태가 좋지 않습니다!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    override fun initAfterBinding() {

    }
}