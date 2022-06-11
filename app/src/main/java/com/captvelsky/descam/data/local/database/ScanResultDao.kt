package com.captvelsky.descam.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScanResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(scanResult: ScanResultLocalObject)

    @Delete
    fun delete(scanResult: ScanResultLocalObject)

    @Query("SELECT * from scanResultLocalObject ORDER BY id ASC")
    fun getAllScanResult(): LiveData<List<ScanResultLocalObject>>
}