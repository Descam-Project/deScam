package com.captvelsky.descam.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val preferences: SettingPreferences
) {
    fun getTheme(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    suspend fun setTheme(state: Boolean) {
        preferences.saveThemeSetting(state)
    }

    suspend fun setUserEmail(email: String) {
        preferences.setUserEmail(email)
    }

    fun getUserEmail(): Flow<String?> = preferences.getUserEmail()
}