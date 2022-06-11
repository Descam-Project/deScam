package com.captvelsky.descam.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    fun saveUserEmail(email: String) {
        viewModelScope.launch {
            repository.setUserEmail(email)
        }
    }
}