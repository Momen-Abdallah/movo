package com.example.movo.data.local

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Movie")
data class Movie(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("q")
    val q: String,
    @SerializedName("qid")
    val qid: String,
    @SerializedName("stars")
    val stars: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: Int
)