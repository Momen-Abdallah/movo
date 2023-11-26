package com.example.movo.ui.movieScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movo.data.local.Movie
import com.example.movo.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    val movieRepository: MovieRepository
) :ViewModel(){
//    private var _searchedMovies  =  MutableLiveData<PagingData<Movie>>(PagingData.empty())
    private var _searchedMovies  =  MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchedMovies
        get() = _searchedMovies

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
//                .cachedIn(viewModelScope)
                .collect{
                    _searchedMovies.value = it
                }
        }
    }
}