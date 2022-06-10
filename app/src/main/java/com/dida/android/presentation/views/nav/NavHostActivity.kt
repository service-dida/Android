package com.dida.android.presentation.views.nav

import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dida.android.GlobalApplication
import com.dida.android.R
import com.dida.android.databinding.ActivityNavHostBinding
import com.dida.android.presentation.base.BaseActivity
import com.dida.android.presentation.viewmodel.nav.NavHostViewModel
import com.dida.android.presentation.views.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : BaseActivity<ActivityNavHostBinding, NavHostViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_nav_host // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: NavHostViewModel by viewModels()

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
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavi.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id ==R.id.myPageFragment){
                loginCheck()
            }
        }
    }
    private fun loginCheck() {
        val accessToken = GlobalApplication.mySharedPreferences.getAccessToken()
        if (accessToken.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            registerForActivityResult.launch(intent)
        }
    }

    val registerForActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == 0) {
            navController.popBackStack()
        }
    }

}
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.mainFragment -> showBottomNav()
//                R.id.bookmarkFragment -> showBottomNav()
//                R.id.messageFragment -> showBottomNav()
//                R.id.mypageFragment -> showBottomNav()
//                else -> hideBottomNav()
//            }
//        }

//        binding.bottomNavi.setupWithNavController(navController)
//    private fun showBottomNav() {
//        binding.bottomNavi.visibility = View.VISIBLE
//    }
//
//    private fun hideBottomNav() {
//        binding.bottomNavi.visibility = View.GONE
//    }