package com.example.movo.ui.homeScreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movo.data.local.Movie
import com.example.movo.databinding.MovieListItemBinding
import com.example.movo.utils.MovieComparator

class MoviePagingAdapter(val context : Context) : PagingDataAdapter<Movie, MoviePagingAdapter.Movie1ViewHolder>(MovieComparator()
) {
    class Movie1ViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onBindViewHolder(holder: Movie1ViewHolder, position: Int) {

//        holder.binding.title.text = getItem(position)!!.title
        holder.binding.rating.text = getItem(position)!!.voteAverage.toString().substring(0,3)
        try {

            holder.binding.posterImage.load("https://image.tmdb.org/t/p/w500/" + getItem(position)!!.posterPath)
//            Glide.with(context).load(movies[position].imageurl!![0]).into(holder.binding.posterImage)

        }catch (e : Exception){
            holder.binding.posterImage.load("https://www.shutterstock.com/image-vector/link-disconnected-line-icon-vector-260nw-1881824773.jpg")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Movie1ViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Movie1ViewHolder(binding)
    }
}