package com.captvelsky.descam.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.databinding.ActivityMainBinding
import com.captvelsky.descam.ui.activity.HomeActivity.Companion.EXTRA_EMAIL
import com.captvelsky.descam.ui.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            userAuth()
        }, 2000)
    }

    private fun userAuth() {
        viewModel.viewModelScope.launch {
            viewModel.getUserEmail().collect { email ->
                if (email.isNullOrEmpty()) {
                    Intent(this@MainActivity, AuthActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    Intent(this@MainActivity, HomeActivity::class.java).also {
                        intent.putExtra(EXTRA_EMAIL, email)
                        startActivity(it)
                        finish()
                    }
                }
            }
        }
    }
}