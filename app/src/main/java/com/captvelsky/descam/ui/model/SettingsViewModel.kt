package com.captvelsky.descam.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    fun getTheme() = repository.getTheme()

    fun setTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.setTheme(isDarkModeActive)
        }
    }

    fun getUserEmail(): Flow<String?> = repository.getUserEmail()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}