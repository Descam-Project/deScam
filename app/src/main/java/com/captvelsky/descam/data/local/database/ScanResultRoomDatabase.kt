package com.captvelsky.descam.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ScanResultLocalObject::class], version = 1)
abstract class ScanResultRoomDatabase : RoomDatabase() {
    abstract fun scanResultDao() : ScanResultDao

    companion object {
        @Volatile
        private var INSTANCE: ScanResultRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ScanResultRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ScanResultRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScanResultRoomDatabase::class.java,
                        "scan_result_database"
                    ).build()
                }
            }
            return INSTANCE as ScanResultRoomDatabase
        }
    }
}