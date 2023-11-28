package com.example.movo.ui.movieInfo

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.movo.databinding.MovieInfoBinding
import com.example.movo.ui.homeScreen.adapters.MovieLoadStateAdapter
import com.example.movo.ui.movieInfo.adapters.MovieInfoRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieInfoScreen : Fragment() {
    private var _binding: MovieInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MovieInfoScreen()
    }

    private val viewModel: MovieInfoViewModel by viewModels()

     val navArgs : MovieInfoScreenArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieInfoBinding.inflate(inflater, container, false)

        //requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val webView = binding.webView
//        binding.videoPlayIcon.setOnClickListener {
//            binding.poster.visibility = View.INVISIBLE
//            binding.videoPlayIcon.visibility = View.INVISIBLE
//            binding.webView.visibility = View.VISIBLE
//
//            lifecycleScope.launch(Dispatchers.IO) {
//                viewModel.getMovieVideos(navArgs.movie.id).collect{
//                    if (it != null){
//                        withContext(Dispatchers.Main){
//                            if (it.movieVideoData.isNotEmpty()){
//                                val key = it.movieVideoData[0].key
//
//                                try {
//                                    val video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${key}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
//                                    val video2 = "<iframe width=\"100%\" height=\"100%\" src=\"https://vidsrc.xyz/embed/${navArgs.movie.id}/color-15006D\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
//                                    webView.loadData(video , "text/html","utf-8")
//                                    webView.settings.javaScriptEnabled = true
//                                    webView.webChromeClient = WebChromeClient()
//                                }catch (e : Exception){
//                                    Toast.makeText(requireContext(), "there is no trailer", Toast.LENGTH_SHORT).show()
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
//        }

        binding.playMovie.setOnClickListener {
            val action = MovieInfoScreenDirections.actionMovieInfoScreenToStreamingScreen(navArgs.movie.id.toString())
            findNavController().navigate(action)
        }
        binding.playTrailer.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getMovieVideos(navArgs.movie.id!!).collect{
                        if (it != null && it.movieVideoData.isNotEmpty()){
                            val action = MovieInfoScreenDirections.actionMovieInfoScreenToStreamingScreen(it.movieVideoData[0].key)
                            findNavController().navigate(action)
                        }else{
                            Toast.makeText(requireContext(), "There is no trailer for this video ", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        val decorView: View = requireActivity().window.decorView
        val windowInsetsController = decorView.windowInsetsController ?: return
        // Configure the behavior of the hidden system bars
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsets.Type.systemBars())
//        WindowCompat.getInsetsController(requireActivity().window,requireActivity().window.decorView).apply {
//            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            hide(WindowInsetsCompat.Type.statusBars())
//        }

        binding.poster.load("https://image.tmdb.org/t/p/w500/"+navArgs.movie.posterPath)
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val adapter = MovieInfoRecyclerViewAdapter(requireContext())
        viewModel.getSimilarMovies(navArgs.movie.id!!)
        binding.similarRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter(){adapter.retry()}
        )


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.similarMovies.collect{
                adapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            var i = 0
//            while (true){
//                delay(1000)
//                binding.similarRecyclerView.scrollToPosition(i)
//                i++
//            }
//        }


//        val webView = binding.webView
//        binding.title.text = navArgs.movie.title

//        binding.title.setOnClickListener {
//            requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://movie-web.app/media/tmdb-movie-${navArgs.movie.id}")))
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            viewModel.getMovieVideos(navArgs.movie.id).collect{
//                if (it != null){
//                    withContext(Dispatchers.Main){
//                        if (it.movieVideoData.isNotEmpty()){
//                            val key = it.movieVideoData[0].key
//
//                            try {
//                                val video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${key}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
//                                val video2 = "<iframe width=\"100%\" height=\"100%\" src=\"https://vidsrc.xyz/embed/${navArgs.movie.id}/color-15006D\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
//                                webView.loadData(video2 , "text/html","utf-8")
//                                webView.settings.javaScriptEnabled = true
//                                webView.webChromeClient = WebChromeClient()
//                            }catch (e : Exception){
//                                Toast.makeText(requireContext(), "there is no trailer", Toast.LENGTH_SHORT).show()
//                            }
//
//                        }
//                    }
//                }
//            }
//        }

    }
}