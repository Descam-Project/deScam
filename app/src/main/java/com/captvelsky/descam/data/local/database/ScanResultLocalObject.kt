package com.captvelsky.descam.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ScanResultLocalObject(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "result")
    var result: String
) : Parcelable
