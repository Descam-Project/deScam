package com.captvelsky.descam.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.captvelsky.descam.ui.fragment.GalleryFragment
import com.captvelsky.descam.ui.fragment.ScanFragment
import com.captvelsky.descam.ui.fragment.TextFragment

class PagerAdapter(fragmentActivity: ScanFragment, data: Bundle) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragmentBundle: Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = GalleryFragment()
            1 -> fragment = TextFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}