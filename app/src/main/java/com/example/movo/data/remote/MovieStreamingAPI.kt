package com.example.movo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieStreamingAPI {
    @GET("embed/movie")
     fun getMovieUrl(@Query("imdb") id : String)

}