package com.captvelsky.descam.data.remote.response

import com.google.gson.annotations.SerializedName

data class TextUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field: SerializedName("message")
    val message: String,

)

data class UploadRequest(
    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Text")
    val text: String,

    @field:SerializedName("Result")
    val result: String
)
