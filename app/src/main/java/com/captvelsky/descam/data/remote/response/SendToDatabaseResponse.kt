package com.captvelsky.descam.data.remote.response

import com.google.gson.annotations.SerializedName

data class SendToDatabaseResponse(
    @field:SerializedName("error")
    val error: String,

    @field: SerializedName("status")
    val status: String,

    @field: SerializedName("message")
    val message: String
)

data class ScanResult(
    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Text")
    val text: String,

    @field:SerializedName("Result")
    val result: String
)

data class ScannedTextResponse(
    @field:SerializedName("output")
    val output: String,

    @field: SerializedName("success")
    val success: Boolean,
)
