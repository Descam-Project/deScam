package com.captvelsky.descam.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.captvelsky.descam.R
import com.captvelsky.descam.adapter.PagerAdapter
import com.captvelsky.descam.databinding.FragmentScanBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()

    }

    //Init tab layout & viewpager
    private fun initTabLayout() {
        val tabLayout: TabLayout = binding?.tabLayout!!
        val viewPager: ViewPager2 = binding?.viewPager!!
        val pagerAdapter = PagerAdapter(this, bundle)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_gallery, R.string.tab_text)
    }
}