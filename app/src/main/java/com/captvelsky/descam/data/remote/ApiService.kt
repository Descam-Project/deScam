package com.captvelsky.descam.data.remote

import com.captvelsky.descam.data.remote.response.SendTextResponse
import com.captvelsky.descam.data.remote.response.TextUploadResponse
import com.captvelsky.descam.data.remote.response.UploadRequest
import retrofit2.http.*

interface ApiService {

    @POST("descamproject.et.r.appspot.com/History/addHistory")
    suspend fun uploadResponse(
        @Body request: UploadRequest
    ) : TextUploadResponse

    @POST("descam/predict")
    suspend fun sendText(
        @Field("input") input: String
    ) : SendTextResponse
}