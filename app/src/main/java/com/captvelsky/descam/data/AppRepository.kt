package com.captvelsky.descam.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.captvelsky.descam.data.remote.ApiService
import com.captvelsky.descam.data.remote.response.SendTextResponse
import com.captvelsky.descam.data.remote.response.TextUploadResponse
import com.captvelsky.descam.data.remote.response.UploadRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val preferences: SettingPreferences,
    private val apiService: ApiService
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

    suspend fun logout() {
        preferences.logout()
    }

    fun getUserEmail(): Flow<String?> = preferences.getUserEmail()

    suspend fun getUploadResult(
        email: String,
        text: String,
        result: String
    ): Flow<Result<TextUploadResponse>> = flow {
        try {
            val response = apiService.uploadResponse(UploadRequest(email, text, result))
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun sendText(
        input: String
    ): Flow<Result<SendTextResponse>> = flow {
        try {
            val response = apiService.sendText(input)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}