package com.captvelsky.descam.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.captvelsky.descam.data.remote.ApiServiceDatabase
import com.captvelsky.descam.data.remote.ApiServiceModel
import com.captvelsky.descam.data.remote.response.ScanRequest
import com.captvelsky.descam.data.remote.response.ScannedTextResponse
import com.captvelsky.descam.data.remote.response.SendToDatabaseResponse
import com.captvelsky.descam.data.remote.response.ScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val preferences: SettingPreferences,
    private val apiServiceModel: ApiServiceModel,
    private val apiServiceDatabase: ApiServiceDatabase
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

    suspend fun sendScanResult(
        email: String,
        text: String,
        result: String
    ): Flow<Result<SendToDatabaseResponse>> = flow {
        try {
            val response =
                apiServiceDatabase.sendScanResultToDatabase(ScanResult(email, text, result))
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun sendText(
        input: String
    ): Flow<Result<ScannedTextResponse>> = flow {
        try {
            val response = apiServiceModel.sendTextToScan(ScanRequest(input))
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}