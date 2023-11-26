package com.example.movo.ui.movieInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movo.databinding.MovieInfoBinding

class MovieInfoScreen : Fragment() {
    private var _binding: MovieInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MovieInfoScreen()
    }

    private val viewModel: MovieInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieInfoBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}