package com.example.movo.data.local


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("data")
    val data: List<Movie>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long
)