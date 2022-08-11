package com.dida.android.presentation.views.nav.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.mypage.MyPageFragmentArgs
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

    override fun initStartView() {
        /*
        MyPage 에서 지갑으로 왔을 경우에는 Args 값이 null 이 아니므로 현재 Fragment 에서 동작
        아닐 경우 에는 MyPage Fragment 로 이동을 한다.
        * */
        val args: AddFragmentArgs by navArgs()
        if(args == null) {
            val argument = true
            val action = AddFragmentDirections.actionAddFragmentToMyPageFragment(argument)
            navController.navigate(action)
            navController.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}