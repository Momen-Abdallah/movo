package com.example.movo.data.local


import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val movieVideoData: List<MovieVideoData>
)