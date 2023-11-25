package com.example.movo.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movo.R
import com.example.movo.databinding.HomeScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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





//        binding.viewPager.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        var response : Response<MovieResponse>? = null;
        setupCarousel()
//        lifecycleScope.launch {
//            mViewModel.searchMovie("batman")
//            mViewModel.searchedMovies.observe(viewLifecycleOwner){
//                when(it.status){
//                    DataStatus.Status.LOADING->{
//                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
//                    }
//                    DataStatus.Status.SUCCESS->{
//                        binding.viewPager.adapter = MovieViewPagerAdapter(requireContext(),it.data!!.data)
//                        dataSize = it.data.data.size
//                        viewPagerActive.value = true
//                        binding.indicator.setViewPager(binding.viewPager)
//                    }
//                    DataStatus.Status.ERROR->{
//                        Toast.makeText(requireContext(), "Error during the data request", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//
//            }
//        }


//        var movies = listOf<Movie>()
        val adapter = MoviePagingAdapter(requireContext())

        binding.apply {

            recyclerView.layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            recyclerView.setHasFixedSize(true)

            recyclerView.adapter = adapter.withLoadStateFooter(
                MovieLoadStateAdapter(){adapter.retry()}
            )

            viewPager.adapter =  adapter.withLoadStateFooter(
                    MovieLoadStateAdapter(){adapter.retry()}
            )

        }
        mViewModel.searchMovie("batman")



//        mViewModel.searchedMovies.observe(viewLifecycleOwner){
//            adapter.submitData(lifecycle,it)
//        }


        lifecycleScope.launch {
            viewPagerActive.value = true
            mViewModel.searchedMovies.collect {
                adapter.submitData(lifecycle,it)
            }
        }


       // binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        lifecycleScope.launch {
//            movies = Retrofit.Builder()
//                .baseUrl(Utils.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
////            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
//                .build().create(MovieApi::class.java).search(1,"batman").results.filter { !it.imageurl.isNullOrEmpty() }.take(10)
//
//
//            val adapter= Movie1PagingAdapter(requireContext())
//
//            mViewModel.search("Django").collect{
//                adapter.submitData(it)
//            }
//
//            binding.viewPager.adapter = adapter
//            viewPagerActive.value = true
//            mViewModel.search("Django").collect{
//
//                val adapter = Movie1PagingAdapter(requireContext()).withLoadStateFooter(MovieLoadStateAdapter())
//                adapter.notifyDataSetChanged()
//                binding.recyclerView.adapter = adapter
//
//            }
        }.invokeOnCompletion {
//            if (movies.isNotEmpty())
//                Toast.makeText(requireContext(), "${movies.size}", Toast.LENGTH_SHORT).show()
//            binding.viewPager.adapter = MovieViewPagerAdapter(
//                requireContext(),
//                movies
//            )
        }

        viewPagerActive.observe(viewLifecycleOwner){
            if (it)
                lifecycleScope.launch {
                    while (it) {
                        delay(2000)
//                        if (binding.viewPager.currentItem == dataSize!!-1)
//                            binding.viewPager.currentItem = 0
//                        else
                            binding.viewPager.currentItem++
                    }
                }
        }
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
        _binding = null
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