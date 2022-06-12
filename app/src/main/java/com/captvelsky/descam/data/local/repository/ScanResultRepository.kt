package com.captvelsky.descam.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.captvelsky.descam.data.local.database.ScanResultDao
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.data.local.database.ScanResultRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanResultRepository(application: Application) {
    private val mScanResultDao: ScanResultDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ScanResultRoomDatabase.getDatabase(application)
        mScanResultDao = db.scanResultDao()
    }

    fun getAllScanResult(): LiveData<List<ScanResultLocalObject>> =
        mScanResultDao.getAllScanResult()

    fun insert(scanResultLocalObject: ScanResultLocalObject) {
        executorService.execute { mScanResultDao.insert(scanResultLocalObject) }
    }

    fun delete(scanResultLocalObject: ScanResultLocalObject) {
        executorService.execute { mScanResultDao.delete(scanResultLocalObject) }
    }
}