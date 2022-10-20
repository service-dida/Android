package com.dida.android.presentation.adapter.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dida.android.presentation.views.createcommunity.CreateCommunityNftFragment

class CreateCommunityNftPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val NUM_TAB = 2

    override fun getItemCount(): Int {
        return NUM_TAB
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CreateCommunityNftFragment(0)
            1 -> return CreateCommunityNftFragment(1)
        }
        return CreateCommunityNftFragment(0)
    }
}