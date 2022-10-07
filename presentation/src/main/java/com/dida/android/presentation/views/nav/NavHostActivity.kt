package com.dida.android.presentation.views.nav

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dida.android.R
import com.dida.android.databinding.ActivityNavHostBinding
import com.dida.android.presentation.base.BaseActivity
import com.dida.android.presentation.views.login.LoginActivity
import com.dida.data.DataApplication.Companion.dataStorePreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailNftFragment -> hideBottomNav()
                R.id.myPageFragment -> {
                    loginCheck()
                    showBottomNav()
                }
                R.id.addFragment -> loginCheck()
                R.id.communityDetailFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
        binding.bottomNavi.setupWithNavController(navController)

        // ADD 버튼 클릭
        binding.bottomNavi.menu.getItem(2).isEnabled = false
        binding.bottomNaviAddBtn.setOnClickListener {
            navController.navigate(R.id.addFragment)
        }

        // 중복터치 막기!!
        binding.bottomNavi.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {}
                R.id.myPageFragment -> {}
                R.id.addFragment -> {}
                R.id.communityFragment -> {}
            }
        }
    }

    private fun loginCheck() {
        runBlocking {
            if(dataStorePreferences.getAccessToken().isNullOrEmpty()) {
                registerForActivityResult.launch(Intent(this@NavHostActivity, LoginActivity::class.java))
            }
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
