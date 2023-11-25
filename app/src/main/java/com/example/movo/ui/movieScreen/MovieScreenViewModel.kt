package com.example.movo.ui.movieScreen

import androidx.lifecycle.ViewModel
import com.example.movo.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    movieRepository: MovieRepository
) :ViewModel(){

}