package com.example.movo.ui.homeScreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.movo.data.local.Movie
import com.example.movo.databinding.MovieListItemBinding

class MovieViewPagerAdapter(val context : Context ,val movies : List<Movie>) : RecyclerView.Adapter<MovieViewPagerAdapter.ViewHolder>(){

    class ViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = movies[position].title
        try {

            holder.binding.posterImage.load("https://image.tmdb.org/t/p/w500/" + movies[position].posterPath)
//            Glide.with(context).load(movies[position].imageurl!![0]).into(holder.binding.posterImage)

        }catch (e : Exception){
            Glide.with(context).load("https://www.shutterstock.com/image-vector/link-disconnected-line-icon-vector-260nw-1881824773.jpg").into(holder.binding.posterImage)
        }

//        holder.binding.card.setOnTouchListener { view, motionEvent ->
//            CoroutineScope(Dispatchers.Main).launch {
//                HomeScreen.viewPagerActive = false
//                delay(10000)
//                HomeScreen.viewPagerActive = true
//
//            }
//
//            true
//        }
//        holder.binding.card.setOnDragListener { view, dragEvent ->
//            HomeScreen.viewPagerActive = false
//            true
//        }
    }


}