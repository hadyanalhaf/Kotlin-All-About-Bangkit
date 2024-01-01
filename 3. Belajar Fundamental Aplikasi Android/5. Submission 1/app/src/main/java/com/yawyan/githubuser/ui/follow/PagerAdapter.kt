package com.yawyan.githubuser.ui.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val followfragment = FollowFragment()
        followfragment.arguments = Bundle().apply {
            putInt(FollowFragment.POSITION, position + 1)
            putString(FollowFragment.USERNAME, username)
        }
        return followfragment
    }


}