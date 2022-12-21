package com.nislav.settleexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.nislav.settleexpenses.databinding.ActivityMainBinding
import com.nislav.settleexpenses.ui.main.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity, holds tabs.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(toolbar)
            val adapter = PagerAdapter(this@MainActivity)
            viewPager.adapter = adapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = adapter.getTitle(position)
            }.attach()
        }
    }
}