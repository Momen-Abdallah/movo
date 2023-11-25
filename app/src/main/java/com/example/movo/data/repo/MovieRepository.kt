package com.example.movo.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movo.data.local.Movie
import com.example.movo.data.local.MoviePagingSource
import com.example.movo.data.local.MovieVideosResponse
import com.example.movo.data.remote.MovieApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


//@Inject constructor
class MovieRepository @Inject constructor(val movieApi: MovieApi){
//    suspend fun searchMovie(query : String)  = flow {
//
////        emit(DataStatus.loading())
//        val result = movieApi.searchMovie(query)
//        when(result.code()){
//            200 -> emit(DataStatus.success(result.body()))
//            400 -> emit(DataStatus.error(result.message()))
//            500 -> emit(DataStatus.error(result.message()))
//        }
//
//    }.catch {
//        emit(DataStatus.error(it.message.toString()))
//    }.flowOn(Dispatchers.IO)
//    suspend fun getFanFavoritesMovies(country : String): Response<MovieResponse> {
//        return movieApi.getFanFavoritesMovies(country = country)
//    }
//    suspend fun getWeekTop10Movies(): Response<MovieResponse>{
//        return movieApi.getWeekTop10Movies()
//    }



    fun searchMovies(
        query : String,
        include_adult : Boolean? = null,
        language: String? = null,
        primary_release_year : String? = null,
        region : String? = null,
        year : String? = null,
    ) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieApi = movieApi ,
                    action = "search",
                    query = query,
                    include_adult = include_adult,
                    language = language,
                    primary_release_year = primary_release_year,
                    region = region,
                    year = year
                )
            }
        ).flow
    }

    fun getPopularMovies(
        language: String? = null,
        region : String? = null,
    ) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieApi = movieApi ,
                    action = "popular",
                    language = language,
                    region = region,
                )
            }
        ).flow
    }

    fun getTopRatedMovies(
        language: String? = null,
        region : String? = null,
    ) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieApi = movieApi ,
                    action = "topRated",
                    language = language,
                    region = region,
                )
            }
        ).flow
    }

    fun getUpcomingMovies(
        language: String? = null,
        region : String? = null,
    ) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieApi = movieApi ,
                    action = "upcoming",
                    language = language,
                    region = region,
                )
            }
        ).flow
    }

    suspend fun getMovieVideosData(
        movieId : Int,
    ) :Response<MovieVideosResponse>{
       return movieApi.getMovieVideos(movieId)
    }

}