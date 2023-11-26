package com.example.movo.ui.movieScreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movo.data.local.Movie
import com.example.movo.databinding.MovieSearchListItemBinding
import com.example.movo.utils.MovieComparator

class MovieSearchAdapter : PagingDataAdapter<Movie,MovieSearchAdapter.MovieSearchViewHolder>(MovieComparator()){
    class MovieSearchViewHolder(val binding: MovieSearchListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.binding.titleText.text = getItem(position)?.title
        holder.binding.ratingText.text = getItem(position)?.voteAverage.toString()
        try {
            holder.binding.posterImage.load("https://image.tmdb.org/t/p/w500/"+getItem(position)!!.posterPath)
        }catch (e: Exception){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieSearchViewHolder {
        return MovieSearchViewHolder(MovieSearchListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
}