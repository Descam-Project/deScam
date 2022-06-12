package com.captvelsky.descam.ui.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.captvelsky.descam.data.AppRepository
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.data.local.repository.ScanResultRepository
import com.captvelsky.descam.data.remote.response.ScannedTextResponse
import com.captvelsky.descam.data.remote.response.SendToDatabaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: AppRepository, private val application: Application) : ViewModel() {
    private val mScanResultRepository: ScanResultRepository = ScanResultRepository(application)

    suspend fun sendTextToModel(
        input: String
    ) : Flow<Result<ScannedTextResponse>> = repository.sendText(input)

    suspend fun sendResultToDatabase(
        email: String,
        text: String,
        result: String
    ): Flow<Result<SendToDatabaseResponse>> = repository.sendScanResult(email, text, result)

    fun insertToLocalDatabase(scanResult: ScanResultLocalObject) {
        mScanResultRepository.insert(scanResult)
    }

    fun getUserEmail(): Flow<String?> = repository.getUserEmail()
}