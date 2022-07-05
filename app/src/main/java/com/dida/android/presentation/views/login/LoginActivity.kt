package com.dida.android.presentation.views.login

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dida.android.R
import com.dida.android.databinding.ActivityLoginBinding
import com.dida.android.presentation.base.BaseActivity
import com.dida.android.presentation.viewmodel.login.LoginViewModel
import com.dida.android.presentation.views.nav.NavHostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun initStartView() {
        initNavController()

        // test
        startActivity(Intent(this, NavHostActivity::class.java))
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.login_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

    }
}