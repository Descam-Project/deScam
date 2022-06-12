package com.captvelsky.descam.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ScanResultLocalObject::class], version = 2, exportSchema = false)
abstract class ScanResultRoomDatabase : RoomDatabase() {
    abstract fun scanResultDao(): ScanResultDao

    companion object {
        @Volatile
        private var INSTANCE: ScanResultRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ScanResultRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ScanResultRoomDatabase::class.java,
                    "scan_result_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}