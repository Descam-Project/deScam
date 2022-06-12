package com.captvelsky.descam.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.databinding.FragmentSettingsBinding
import com.captvelsky.descam.ui.activity.AuthActivity
import com.captvelsky.descam.ui.model.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swithTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }

        darkMode()
        initPrefInfo()
    }

    private fun darkMode() {
        val switch = binding.swithTheme

        viewModel.getTheme().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.isChecked = false
            }
        }
    }

    private fun initPrefInfo() {
        viewModel.viewModelScope.launch {
            viewModel.getUserEmail().collect { email ->
                binding.prefEmailTextView.text = email
            }
        }
        binding.logoutButton.setOnClickListener { logout() }
    }

    private fun logout() {
        viewModel.logout()
        Intent(requireActivity(), AuthActivity::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}