package com.example.movo.ui.movieScreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movo.R
import com.example.movo.databinding.MovieScreenBinding
import com.example.movo.ui.movieScreen.adapters.MovieSearchAdapter
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


@AndroidEntryPoint
class MovieScreen : Fragment() {


    val navArgs :MovieScreenArgs by navArgs()
    private var _binding: MovieScreenBinding? = null
    val viewModel : MovieScreenViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MovieScreenBinding.inflate(inflater, container, false)

//        binding.searchView.requestFocus()
//
//        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }

        val searchBar =  binding.searchBar
        val searchView =  binding.searchView
        val recyclerView =  binding.recyclerView
        val recyclerView2 =  binding.recyclerView2
//        val appBarLayout = binding.appBarLayout

        searchBar.setText(navArgs.query)
        viewModel.searchMovie(navArgs.query)
        var adapter = MovieSearchAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        recyclerView2.setHasFixedSize(true)
        recyclerView2.adapter = adapter

//        searchView.inflateMenu(R.menu.search_view_menu)
//        searchView.setOnMenuItemClickListener {
//
//        }
//        searchView.setupWithSearchBar(searchBar)

//        searchView.requestFocus()
        searchView.setupWithSearchBar(binding.searchBar)
        searchView.editText.addTextChangedListener {
            viewModel.searchMovie(it.toString())
        }
//       searchView.show()
        searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.searchMovie(textView.text.toString())
//            Toast.makeText(requireContext(), textView.text, Toast.LENGTH_SHORT).show()
            binding.searchBar.setText(searchView.text)

            searchView.hide()
            //go to the new activity
            true
        }

//        viewModel.searchedMovies.observe(viewLifecycleOwner){
//            adapter.submitData(lifecycle, it)
//        }

        lifecycleScope.launch {
            viewModel.searchedMovies.collect{
//                adapter = MovieSearchAdapter()
//                recyclerView.adapter = adapter
//                Toast.makeText(requireContext(), "observed", Toast.LENGTH_SHORT).show()

                adapter.submitData(lifecycle, it)

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}