package com.captvelsky.descam.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.R
import com.captvelsky.descam.databinding.ActivityAuthBinding
import com.captvelsky.descam.ui.activity.HomeActivity.Companion.EXTRA_EMAIL
import com.captvelsky.descam.ui.model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAuth.setOnClickListener {
            loginAuth()
        }
    }

    private fun loginAuth() {
        val email = binding.etEmail.text.toString()

        if (binding.etEmail.error == null && email != "") {
            viewModel.viewModelScope.launch {
                viewModel.saveUserEmail(email)
                Intent(this@AuthActivity, HomeActivity::class.java).also {
                    it.putExtra(EXTRA_EMAIL, email)
                    startActivity(it)
                    finish()
                }
            }
        } else {
            Toast.makeText(
                this@AuthActivity,
                resources.getString(R.string.textinput_email_warning),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}