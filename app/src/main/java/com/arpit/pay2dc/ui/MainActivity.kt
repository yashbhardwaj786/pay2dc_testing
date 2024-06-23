package com.arpit.pay2dc.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.arpit.pay2dc.R
import com.arpit.pay2dc.databinding.ActivityMainBinding
import com.arpit.pay2dc.ui.home.CurrencyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.home_view, CurrencyFragment())
            }
        }
    }
}