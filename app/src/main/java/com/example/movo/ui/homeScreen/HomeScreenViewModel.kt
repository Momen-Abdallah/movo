package com.example.movo.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movo.data.local.Movie
import com.example.movo.data.local.MoviePagingSource
import com.example.movo.data.local.MovieVideosResponse
import com.example.movo.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(val movieRepository: MovieRepository) : ViewModel() {

//    private var _searchedMovies  =  MutableLiveData<PagingData<Movie>>()
//    val searchedMovies
//        get() = _searchedMovies
//
//    fun search(title : String)  {
//        _searchedMovies =  movieRepository.search(title) as MutableLiveData<PagingData<Movie>>
//     }

    private var _searchedMovies  =  MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchedMovies
        get() = _searchedMovies

    private var _popularMovies  =  MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val popularMovies
        get() = _popularMovies

    private var _topRatedMovies  =  MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val topRatedMovies
        get() = _topRatedMovies

    private var _upComingMovies  =  MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val upComingMovies
        get() = _upComingMovies
    fun searchMovie(
        query : String,
        include_adult : Boolean? = null,
        language: String? = null,
        primary_release_year : String? = null,
        region : String? = null,
        year : String? = null,
    )  {
        viewModelScope.launch {
            movieRepository.searchMovies(
                query = query,
                include_adult = include_adult,
                language = language,
                primary_release_year = primary_release_year,
                region = region,
                year = year
            )
                .cachedIn(viewModelScope).collect{
                _searchedMovies.value = it
            }
        }
    }

    fun getPopularMovies(
        language: String? = null,
        region : String? = null,
    )  {
        viewModelScope.launch {
            movieRepository.getPopularMovies(
                language = language,
                region = region
            )
                .cachedIn(viewModelScope).collect{
                    _popularMovies.value = it
                }
        }
    }

    fun getTopRatedMovies(
        language: String? = null,
        region : String? = null,
    )  {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies(
                language = language,
                region = region
            )
                .cachedIn(viewModelScope).collect{
                    _topRatedMovies.value = it
                }
        }
    }

    fun getUpcomingMovies(
        language: String? = null,
        region : String? = null,
    )  {
        viewModelScope.launch {
            movieRepository.getUpcomingMovies(
                language = language,
                region = region
            )
                .cachedIn(viewModelScope).collect{
                    _upComingMovies.value = it
                }
        }
    }
    fun getMovieVideosData(
        movieId : Int,
    ) = flow {
        viewModelScope.launch {
            val response =  movieRepository.getMovieVideosData(movieId)
            if (response.isSuccessful)
                emit(response.body())
        }
    }

//     suspend fun getFanFavoritesMovies(country : String): Response<MovieResponse>{
//            return movieRepository.getFanFavoritesMovies(country)
//     }
//    suspend fun getWeekTop10Movies() : Response<MovieResponse> {
//        return viewModelScope.async {
//             movieRepository.getWeekTop10Movies()
//        }.await()
//
//    }

}