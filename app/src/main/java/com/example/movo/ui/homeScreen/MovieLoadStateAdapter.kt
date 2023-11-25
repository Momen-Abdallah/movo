package com.example.movo.ui.homeScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movo.databinding.MovieLoadStateBinding

class MovieLoadStateAdapter(private val retry : () -> Unit) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(val binding: MovieLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error){
            holder.binding.errorText.text = loadState.error.localizedMessage
        }


        if (loadState is LoadState.Error) holder.binding.errorText.visibility = View.VISIBLE else View.GONE
        if (loadState is LoadState.Loading || loadState is LoadState.Error) holder.binding.progressBar.visibility = View.VISIBLE else View.GONE

        holder.binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(MovieLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


}