package com.captvelsky.descam.ui.model

import androidx.lifecycle.ViewModel
import com.captvelsky.descam.data.AppRepository
import com.captvelsky.descam.data.remote.response.ScannedTextResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TextViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    suspend fun sendText(
        input: String
    ): Flow<Result<ScannedTextResponse>> = repository.sendText(input)
}