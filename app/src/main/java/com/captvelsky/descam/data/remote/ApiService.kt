package com.captvelsky.descam.data.remote

import com.captvelsky.descam.data.remote.response.TextUploadResponse
import com.captvelsky.descam.data.remote.response.UploadRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("descamproject.et.r.appspot.com/History/addHistory")
    suspend fun uploadText(
        @Body request: UploadRequest
    ) : TextUploadResponse
}