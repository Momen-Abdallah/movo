package com.example.movo.ui.streamingScreen

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movo.R
import com.example.movo.databinding.StreamingScreenBinding
import com.example.movo.ui.movieScreen.MovieScreenArgs
import com.example.movo.ui.movieScreen.MovieScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [StreamingScreen.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class StreamingScreen : Fragment() {

    val navArgs: StreamingScreenArgs by navArgs()
    private var _binding: StreamingScreenBinding? = null

    //    val viewModel : StreamingScreenViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                findNavController().navigateUp()
            }
        }
         requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StreamingScreenBinding.inflate(inflater, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val webView = binding.webView
        val video = if (navArgs.id.isDigitsOnly()) {
            "<iframe width=\"100%\" height=\"100%\" src=\"https://vidsrc.xyz/embed/${navArgs.id}/color-15006D\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        } else {
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${navArgs.id}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        }
        webView.loadData(video , "text/html","utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()

        webView.settings.setLoadWithOverviewMode(true);
        webView.settings.setUseWideViewPort(false);
        webView.settings.setSupportZoom(false);

    }
}