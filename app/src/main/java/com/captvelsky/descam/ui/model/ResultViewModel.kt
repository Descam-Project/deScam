package com.captvelsky.descam.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.data.local.repository.ScanResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val application: Application) : ViewModel() {
    private val mScanResultRepository: ScanResultRepository = ScanResultRepository(application)

    fun getAllScanResult(): LiveData<List<ScanResultLocalObject>> = mScanResultRepository.getAllScanResult()
}