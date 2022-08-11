package com.dida.android.presentation.views.nav

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dida.android.R
import com.dida.android.databinding.ActivityNavHostBinding
import com.dida.android.presentation.base.BaseActivity
import com.dida.android.presentation.views.login.LoginActivity
import com.dida.android.presentation.views.nav.add.AddFragment
import com.dida.data.DataApplication.Companion.mySharedPreferences
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
//        loginCheck()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailNftFragment -> hideBottomNav()
                R.id.myPageFragment -> {
                    loginCheck()
                    showBottomNav()
                }
                else ->{
                    showBottomNav()
                }
            }
        }
        binding.bottomNavi.setupWithNavController(navController)

        // ADD 버튼 클릭
        binding.bottomNaviAddBtn.setOnClickListener {
            supportFragmentManager
                .beginTransaction().add(R.id.nav_host_fragment_container, AddFragment())
                .commit()
        }


        // 중복터치 막기!!
        binding.bottomNavi.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {}
                R.id.myPageFragment -> {}
            }
        }
    }

    private fun loginCheck() {
        val accessToken = mySharedPreferences.getAccessToken()
        Log.d("jwt_check!!!", accessToken.toString())
        if (accessToken.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            registerForActivityResult.launch(intent)
        }
    }

    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 0) {
                navController.popBackStack()
            }
        }

    private fun showBottomNav() {
        binding.bottomNavi.visibility = View.VISIBLE
        binding.bottomNaviAddBtn.visibility = View.VISIBLE
        binding.bottomNaviAddBackground.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavi.visibility = View.GONE
        binding.bottomNaviAddBtn.visibility =View.GONE
        binding.bottomNaviAddBackground.visibility=View.GONE
    }

}
