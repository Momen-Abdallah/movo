package com.example.movo.data.remote

import com.example.movo.data.local.Movie
import com.example.movo.data.local.MovieResponse
import com.example.movo.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {


    @Headers("X-RapidAPI-Key: ${Utils.API_KEY}", "X-RapidAPI-Host: ${Utils.API_HOST}")
    @GET("searchIMDB")
    fun searchMovie(@Query("query") query : String): Call<MovieResponse>

    @Headers("X-RapidAPI-Key: ${Utils.API_KEY}", "X-RapidAPI-Host: ${Utils.API_HOST}")
    @GET("getFanFavorites")
    fun getFanFavoritesMovies(@Query("country") country : String = "us") : Call<MovieResponse>

    @Headers("X-RapidAPI-Key: ${Utils.API_KEY}", "X-RapidAPI-Host: ${Utils.API_HOST}")
    @GET("getWeekTop10")
    fun getWeekTop10Movies() : Call<MovieResponse>



}