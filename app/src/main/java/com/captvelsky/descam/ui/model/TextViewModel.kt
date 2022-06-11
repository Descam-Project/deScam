package com.captvelsky.descam.ui.model

import androidx.lifecycle.ViewModel
import com.captvelsky.descam.data.AppRepository
import com.captvelsky.descam.data.remote.response.SendTextResponse
import com.captvelsky.descam.data.remote.response.TextUploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TextViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    suspend fun sendText(
        input: String
    ): Flow<Result<SendTextResponse>> = repository.sendText(input)
}