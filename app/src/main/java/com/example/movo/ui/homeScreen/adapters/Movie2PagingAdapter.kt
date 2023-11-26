package com.example.movo.ui.homeScreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movo.data.local.Movie
import com.example.movo.databinding.Movie2ListItemBinding
import com.example.movo.utils.MovieComparator

class Movie2PagingAdapter(val context : Context) : PagingDataAdapter<Movie, Movie2PagingAdapter.Movie1ViewHolder>(
    MovieComparator()
) {
    class Movie1ViewHolder(val binding: Movie2ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onBindViewHolder(holder: Movie1ViewHolder, position: Int) {

        holder.binding.title.text = getItem(position)!!.title.split(" ").let { if (it.size>=2) it[0]+" "+it[1] else it[0]}
        holder.binding.rating.text = getItem(position)!!.voteAverage.toString().substring(0,3)
        try {

            holder.binding.imageView.load("https://image.tmdb.org/t/p/w500/" + getItem(position)!!.posterPath)
//            Glide.with(context).load(movies[position].imageurl!![0]).into(holder.binding.posterImage)

        }catch (e : Exception){
            holder.binding.imageView.load("https://www.shutterstock.com/image-vector/link-disconnected-line-icon-vector-260nw-1881824773.jpg")
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Movie1ViewHolder {
        val binding = Movie2ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Movie1ViewHolder(binding)
    }
}