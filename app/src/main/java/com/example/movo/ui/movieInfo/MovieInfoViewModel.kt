package com.example.movo.ui.movieInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movo.data.local.Movie
import com.example.movo.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieInfoViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private var _similarMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val similarMovies
        get() = _similarMovies

    suspend fun getMovieVideos(id: Int) = flow {
        val result = movieRepository.getMovieVideosData(id)
        if (result.isSuccessful)
            emit(result.body())
    }


    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            movieRepository.getSimilarMovies(movie_id = id)
                .cachedIn(viewModelScope)
                .collect {
                    _similarMovies.value = it
                }

        }
    }

}