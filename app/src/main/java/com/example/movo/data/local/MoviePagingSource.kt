package com.example.movo.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movo.data.remote.MovieApi
import java.lang.Exception

class MoviePagingSource(
    val movieApi: MovieApi,
    val action : String,
    val query: String? = null,
    val include_adult: Boolean? = null,
    val language: String? = null,
    val primary_release_year: String? = null,
    val region: String? = null,
    val year: String? = null,
    val movie_id: Int? = null,
    ) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key?:1
         return try {

             val  response : MovieResponse = when(action){
                 "search"->{
                     movieApi.searchMovies(
                         page = page,
                         query = query!!,
                         include_adult = include_adult,
                         language = language,
                         primary_release_year = primary_release_year,
                         region = region,
                         year = year,
                     )
                 }
                 "popular"->{
                     movieApi.getPopularMovies(
                         page = page,
                         language = language,
                         region = region,
                     )
                 }
                 "topRated"->{
                     movieApi.getTopRatedMovies(
                         page = page,
                         language = language,
                         region = region,
                     )
                 }
                 "upcoming"->{
                     movieApi.getUpcomingMovies(
                         page = page,
                         language = language,
                         region = region,
                     )
                 }
                 "similar"->{
                     movieApi.getSimilarMovies(
                         page = page,
                         movie_id = movie_id!!,
                         language = language,
                     )
                 }
                 else->{
                     movieApi.getUpcomingMovies(
                         page = page,
                         language = language,
                         region = region,
                     )
                 }
             }
             LoadResult.Page(
                 data = response.movies,
                 prevKey = if (page == 1) null else page - 1,
                 nextKey = if (response.movies.isEmpty()) null else page + 1
             )

        }catch (e : Exception){
            return LoadResult.Error(e)
        }
    }
}