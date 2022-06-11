package com.captvelsky.descam.data.remote

import com.captvelsky.descam.data.remote.response.ScanResult
import com.captvelsky.descam.data.remote.response.ScannedTextResponse
import com.captvelsky.descam.data.remote.response.SendToDatabaseResponse
import retrofit2.http.*

interface ApiService {

    @POST("descamproject.et.r.appspot.com/History/addHistory")
    suspend fun sendScanResultToDatabase(
        @Body request: ScanResult
    ) : SendToDatabaseResponse

    @POST("descam/predict")
    suspend fun sendTextToScan(
        @Field("input") input: String
    ) : ScannedTextResponse
}