package com.example.movo.data.local


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    @SerializedName("adult")
    val adult: Boolean? = false,
    @SerializedName("backdrop_path")
    val backdropPath: String? =  "",
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = listOf(1,2),
    @SerializedName("id")
    val id: Int?=  1,
    @SerializedName("original_language")
    val originalLanguage: String?=  "",
    @SerializedName("original_title")
    val originalTitle: String?=  "",
    @SerializedName("overview")
    val overview: String?=  "",
    @SerializedName("popularity")
    val popularity: Double?=  0.1,
    @SerializedName("poster_path")
    val posterPath: String?=  "",
    @SerializedName("release_date")
    val releaseDate: String?=  "",
    @SerializedName("title")
    val title: String?=  "",
    @SerializedName("video")
    val video: Boolean? = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.5,
    @SerializedName("vote_count")
    val voteCount: Int?=  1
) : Parcelable