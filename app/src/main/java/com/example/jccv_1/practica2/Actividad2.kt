package com.example.jccv_1.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jccv_1.R
import com.example.jccv_1.databinding.TabberBinding
import com.google.android.material.tabs.TabLayout


class Actividad2 : AppCompatActivity() {

    private lateinit var binding: TabberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TabberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTabLayout()
        setupViewPager()
        binding.button7.setOnClickListener(){
            finish()
        }

    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.apply {
            addTab(this.newTab().setText(R.string.first_fragment))
            addTab(this.newTab().setText(R.string.second_fragment))
            addTab(this.newTab().setText(R.string.third_fragment))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        binding.viewPager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }
}
