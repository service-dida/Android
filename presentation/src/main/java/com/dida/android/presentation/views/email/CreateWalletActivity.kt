package com.dida.android.presentation.views.email

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dida.android.R
import com.dida.android.databinding.ActivityCreateWalletBinding
import com.dida.android.databinding.ActivityLoginBinding
import com.dida.android.presentation.base.BaseActivity
import com.dida.android.presentation.views.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateWalletActivity : BaseActivity<ActivityCreateWalletBinding, CreateWalletViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_create_wallet

    override val viewModel: CreateWalletViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun initStartView() {
        initNavController()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.create_wallet_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

    }
}