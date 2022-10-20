package com.dida.android.presentation.adapter.community

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dida.android.presentation.views.createcommunity.CreateCommunityNftFragment

class CreateCommunityNftPagerAdapter(fragment: Fragment, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment.childFragmentManager, lifecycle) {

    private val fragmentList = arrayListOf<Fragment>(CreateCommunityNftFragment(0), CreateCommunityNftFragment(1))

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemId(position: Int): Long {
        // generate new id
        return fragmentList[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        // false if item is changed
        return fragmentList.find { it.hashCode().toLong() == itemId } != null
    }
}