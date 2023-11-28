package com.example.movo.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movo.R
import com.example.movo.databinding.HomeScreenBinding
import com.example.movo.ui.homeScreen.adapters.Movie2PagingAdapter
import com.example.movo.ui.homeScreen.adapters.MovieLoadStateAdapter
import com.example.movo.ui.homeScreen.adapters.MoviePagingAdapter
import com.example.movo.ui.homeScreen.decoration.HorizontalMarginItemDecoration
import com.example.movo.ui.movieScreen.adapters.MovieSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class HomeScreen : Fragment() {


    private var _binding: HomeScreenBinding? = null
    private val mViewModel: HomeScreenViewModel by viewModels()

    companion object{
        var dataSize : Int? = null
        var viewPagerActive = MutableLiveData<Boolean>(false)
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeScreenBinding.inflate(inflater, container, false)

//        mViewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val r = object: CountDownTimer(100000, 3000){
//            override fun onTick(p0: Long) {
//                if (viewPagerActive .value!= null&& viewPagerActive.value!!)
//                    binding.viewPager.currentItem++
//            }
//
//            override fun onFinish() {
//                //add your code here
//            }
//        }.start()
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.search_icon ->{
                        binding.searchView.show()
//                        findNavController().navigate(R.id.action_HomeScreen_to_movieScreen)
                        true
                    }

                    else->{
                        true
                    }
                }
            }

        })

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)



        setupCarousel()


        val viewPagerAdapter = MoviePagingAdapter(requireContext())
        val topRatedRecyclerViewAdapter = Movie2PagingAdapter(requireContext())
        val upcomingRecyclerViewAdapter = Movie2PagingAdapter(requireContext())
        val searchMoviesRecyclerViewAdapter = MovieSearchAdapter()


        binding.apply {


            topRatedRecyclerView.layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            topRatedRecyclerView.setHasFixedSize(true)

            topRatedRecyclerView.adapter = topRatedRecyclerViewAdapter.withLoadStateFooter(
                MovieLoadStateAdapter(){topRatedRecyclerViewAdapter.retry()}
            )

            upcomingRecyclerView.layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            upcomingRecyclerView.setHasFixedSize(true)

            upcomingRecyclerView.adapter = upcomingRecyclerViewAdapter.withLoadStateFooter(
                MovieLoadStateAdapter(){upcomingRecyclerViewAdapter.retry()}
            )
            viewPager.adapter =  viewPagerAdapter.withLoadStateFooter(
                    MovieLoadStateAdapter(){viewPagerAdapter.retry()}
            )

//            binding.recyclerView.layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//            binding.recyclerView.adapter = searchMoviesRecyclerViewAdapter
//                .withLoadStateFooter(
//                MovieLoadStateAdapter(){upcomingRecyclerViewAdapter.retry()}
//            )

        }
        mViewModel.getPopularMovies()
        mViewModel.getTopRatedMovies()
        mViewModel.getUpcomingMovies()




//        binding.searchView.editText.addTextChangedListener {
//
//              if (it != null&&it.toString().length>=5)
//            mViewModel.searchMovie(it.toString())
//        }

        binding.searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->

           val text = textView.text.toString()
            if (textView.text.isNotEmpty()){
                binding.searchView.clearText()
                findNavController().navigate(HomeScreenDirections.actionHomeScreenToMovieScreen(text))
//                r.cancel()

            }
            else
                binding.searchView.show()

            true
        }


//        mViewModel.searchedMovies.observe(viewLifecycleOwner){
//            adapter.submitData(lifecycle,it)
//        }
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.topRatedMovies.collect {
                topRatedRecyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.upComingMovies.collect {
                upcomingRecyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewPagerActive.value = true
            mViewModel.popularMovies.collect {
                viewPagerAdapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }



        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Unconfined) {
            while (viewPagerActive .value!= null&& viewPagerActive.value!!){
                delay(3000)
                withContext(Dispatchers.Main) {binding.viewPager.currentItem++}
            }
        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            mViewModel.searchedMovies.collect {
//                searchMoviesRecyclerViewAdapter.submitData(lifecycle,it)
//            }
//        }


       // binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        lifecycleScope.launch {
////            movies = Retrofit.Builder()
////                .baseUrl(Utils.BASE_URL)
////                .addConverterFactory(GsonConverterFactory.create())
//////            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
////                .build().create(MovieApi::class.java).search(1,"batman").results.filter { !it.imageurl.isNullOrEmpty() }.take(10)
////
////
////            val adapter= Movie1PagingAdapter(requireContext())
////
////            mViewModel.search("Django").collect{
////                adapter.submitData(it)
////            }
////
////            binding.viewPager.adapter = adapter
////            viewPagerActive.value = true
////            mViewModel.search("Django").collect{
////
////                val adapter = Movie1PagingAdapter(requireContext()).withLoadStateFooter(MovieLoadStateAdapter())
////                adapter.notifyDataSetChanged()
////                binding.recyclerView.adapter = adapter
////
////            }
//        }.invokeOnCompletion {
////            if (movies.isNotEmpty())
////                Toast.makeText(requireContext(), "${movies.size}", Toast.LENGTH_SHORT).show()
////            binding.viewPager.adapter = MovieViewPagerAdapter(
////                requireContext(),
////                movies
////            )
//        }




//        timer(initialDelay = 3000L, period = 10000L ) {
////            lifecycleScope.launch {
////                while (it) {
////                    delay(3000L)
//                    if (binding.viewPager.currentItem == response?.body()!!.data.size-1)
//                        binding.viewPager.currentItem = 0
//                    else
//                        binding.viewPager.currentItem++
////                }
////            }
//        }



//        lifecycleScope.launch(Dispatchers.IO){
//             response = mViewModel.searchMovie("Django")
//
//
//
//        }.invokeOnCompletion {
//            if (response?.isSuccessful == true){
//                binding.viewPager.adapter = MovieViewPagerAdapter(requireContext(),response?.body()!!.data)
//                viewPagerActive = true
//                lifecycleScope.launch {
//                    while (viewPagerActive) {
//                        delay(2000)
//                        if (!viewPagerActive)
//                            yield()
//                        if (binding.viewPager.currentItem == response?.body()!!.data.size-1)
//                            binding.viewPager.currentItem = 0
//                        else
//                            binding.viewPager.currentItem++
//                    }
//                }
//            }
//        }




//        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                viewPagerActive = false
//            }
//        })




//        lifecycleScope.launch {
//            mViewModel.searchMovie("Django").data
//        }.invokeOnCompletion {
//
//        }

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        // Assuming you have the WebView with ID "webView" in your layout XML

//        binding.webView.loadUrl("https://vidsrc.xyz/embed/movie?imdb=tt9603212")


    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun onPause() {
        super.onPause()

    }

    fun setupCarousel() {


        val viewPager = binding.viewPager
        viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
        }
        viewPager.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager.addItemDecoration(itemDecoration)



    }

    override fun onDestroy() {
        super.onDestroy()
        viewPagerActive.value = false
    }

}