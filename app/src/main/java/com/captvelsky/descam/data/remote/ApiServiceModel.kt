package com.captvelsky.descam.data.remote

import com.captvelsky.descam.data.remote.response.ScanRequest
import com.captvelsky.descam.data.remote.response.ScanResult
import com.captvelsky.descam.data.remote.response.ScannedTextResponse
import com.captvelsky.descam.data.remote.response.SendToDatabaseResponse
import retrofit2.http.*

interface ApiServiceModel {
    @POST("descam/predict")
    suspend fun sendTextToScan(
        @Body text: ScanRequest
    ): ScannedTextResponse
}

interface ApiServiceDatabase {
    @POST("History/addHistory")
    suspend fun sendScanResultToDatabase(
        @Body request: ScanResult
    ): SendToDatabaseResponse
}