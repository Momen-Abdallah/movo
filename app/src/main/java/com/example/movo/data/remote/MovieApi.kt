package com.example.movo.data.remote

import com.example.movo.data.local.MovieResponse
import com.example.movo.data.local.MovieVideosResponse
import com.example.movo.utils.Utils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int? = null,
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean? = null,
        @Query("language") language: String? = null,
        @Query("primary_release_year") primary_release_year: String? = null,
        @Query("region") region: String? = null,
        @Query("year") year: String? = null,
    ) : MovieResponse

    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ) : MovieResponse


    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ) : MovieResponse

    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ) : MovieResponse

    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String? = null,
    ) : Response<MovieVideosResponse>

    @Headers("accept: ${Utils.ACCEPT_HEADER}","Authorization: Bearer ${Utils.ACCESS_TOKEN_HEADER}")
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ) : MovieResponse
}