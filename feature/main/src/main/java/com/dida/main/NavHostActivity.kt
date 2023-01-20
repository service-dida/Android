package com.dida.main

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dida.android.NavigationGraphDirections
import com.dida.common.base.BaseActivity
import com.dida.main.databinding.ActivityNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : BaseActivity<ActivityNavHostBinding, NavHostViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_nav_host // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: NavHostViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    var waitTime = 0L

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
                R.id.homeFragment -> showBottomNav()
                R.id.communityFragment -> showBottomNav()
                R.id.defiFragment -> showBottomNav()
                R.id.myPageFragment -> showBottomNav()
                else -> hideBottomNav()
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
                R.id.defiFragment -> {}
                R.id.myPageFragment -> {}
                R.id.addFragment -> {}
                R.id.communityFragment -> {}
            }
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

    override fun onBackPressed() {
        try {
            if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                super.onBackPressed()
            } else {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment -> {
                        if(System.currentTimeMillis()-waitTime >= 1500) {
                            waitTime = System.currentTimeMillis()
                            Toast.makeText(this,"뒤로가기 버튼을 \n한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
                        } else {
                            finishAffinity() // 액티비티 종료
                        }
                    }
                    null -> super.onBackPressed()
                    else -> navController.navigate(NavigationGraphDirections.actionMainFragment())
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
