package com.test.moviebox.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.test.moviebox.R
import com.test.moviebox.base.BaseActivity
import com.test.moviebox.databinding.ActivityMovieListBinding
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.utils.BaseWorker
import com.test.moviebox.utils.Constant.MovieFilterCategory.NOW_PLAYING
import com.test.moviebox.utils.Constant.MovieFilterCategory.POPULAR
import com.test.moviebox.utils.Constant.MovieFilterCategory.TOP_RATED
import com.test.moviebox.utils.Constant.MovieFilterCategory.UPCOMING
import com.test.moviebox.utils.NewResource
import com.test.moviebox.utils.PaginationScrollListener
import com.test.moviebox.utils.Status
import com.test.moviebox.view.adapter.MovieListPaginationAdapter
import com.test.moviebox.view.dialog.CategoryBottomSheetFragment
import com.test.moviebox.viewmodel.MovieViewModel
import com.test.moviebox.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.partial_main_toolbar.view.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MovieListActivity : BaseActivity() {
    lateinit var binding: ActivityMovieListBinding
    lateinit var movieViewModel: MovieViewModel
    lateinit var paginationAdapter: MovieListPaginationAdapter
    var type =  ""

    private var isLoadingPage = false
    private var isLastPagePage = false
    private var TOTAL_PAGES = 5
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        setupAdapter()
        initViewModel()
        setListener()
        loadFirstPage()
        testWorker()
    }

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance().cancelUniqueWork("movie-list")
        Log.i("lifecyaaa","ondestroy called")
    }

    fun testWorker(){
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val myWorkBuilder = PeriodicWorkRequest.Builder(
            BaseWorker::class.java,
            15,
            TimeUnit.MINUTES
        )

        val myWork = myWorkBuilder
            .setConstraints(constraints)
            .addTag("movie-list")
            .build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork("movie-list", ExistingPeriodicWorkPolicy.KEEP, myWork)
    }

    private fun loadFirstPage() {
        binding.loadingBarMovieList.visibility = View.VISIBLE
        when(type){
            POPULAR -> {
                movieViewModel.fetchPopularMovies(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadMovieFirst(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            UPCOMING -> {
                movieViewModel.fetchUpComingMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadMovieFirst(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            TOP_RATED -> {
                movieViewModel.fetchTopRatedMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadMovieFirst(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            NOW_PLAYING -> {
                movieViewModel.fetchNowPlayingMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadMovieFirst(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            else -> {
                lifecycleScope.launch { movieViewModel.fetchMovieListNew(currentPage) }
                movieViewModel.movieList.observe(this, Observer { response ->
                    when (response) {
                        is NewResource.Success -> {
                            response.data?.let { picsResponse ->
                                successLoadMovieFirst(picsResponse)
                            }
                        }

                        is NewResource.Error -> {
                            Toast.makeText(this,response.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun setListener(){
        binding.let {
            it.toolbar.iv_nav_favorite.setOnClickListener {
                startActivity(Intent(this,FavoriteMovieListActivity::class.java))
            }

            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.rvMovieList.layoutManager = layoutManager
            it.rvMovieList.addOnScrollListener(object : PaginationScrollListener(layoutManager){
                override fun loadMoreItems() {
                    isLoadingPage = true
                    currentPage += 1
                    loadNextPage()
                }
                override val isLastPage: Boolean
                    get() = isLastPagePage
                override val isLoading: Boolean
                    get() = isLoadingPage

            })

            it.btnCategory.setOnClickListener {
                val dialog = CategoryBottomSheetFragment.newInstance(type)
                dialog.onClicked = { category ->
                    this.type = category
                    paginationAdapter.list.clear()
                    currentPage = 1
                    TOTAL_PAGES = 0
                    loadFirstPage()
                    paginationAdapter.notifyDataSetChanged()
                }
                dialog.show(supportFragmentManager,"CategoryBottomSheetFragment")
            }
        }
    }

    private fun loadNextPage() {
        when(type){
            POPULAR -> {
                movieViewModel.fetchPopularMovies(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadNextMovie(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            UPCOMING -> {
                movieViewModel.fetchUpComingMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadNextMovie(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            TOP_RATED -> {
                movieViewModel.fetchTopRatedMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadNextMovie(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            NOW_PLAYING -> {
                movieViewModel.fetchNowPlayingMovie(currentPage).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> successLoadNextMovie(resource.data)
                            Status.ERROR -> errorLoadMovie(resource.message)
                        }
                    }
                })
            }
            else -> {
                lifecycleScope.launch { movieViewModel.fetchMovieListNew(currentPage) }
                movieViewModel.movieList.observe(this, Observer { response ->
                    when (response) {
                        is NewResource.Success -> {
                            response.data?.let { picsResponse ->
                                successLoadNextMovie(picsResponse)
                            }
                        }

                        is NewResource.Error -> {
                            Toast.makeText(this,response.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun setupAdapter(){
        paginationAdapter = MovieListPaginationAdapter(this)
        binding.let {
            it.rvMovieList.setHasFixedSize(true)
            it.rvMovieList.setItemViewCacheSize(100)
            paginationAdapter.setHasStableIds(true)
            paginationAdapter.onClicked = {id ->
                startActivity(MovieDetailActivity.getStartIntent(this,id,false))
            }
            it.rvMovieList.adapter = paginationAdapter
        }
    }

    private fun initViewModel(){
        val movieViewModelFactory = MovieViewModelFactory()
        movieViewModel = ViewModelProviders.of(this,movieViewModelFactory).get(
            MovieViewModel::class.java)
    }

    private fun successLoadNextMovie(response: MovieListResponse?){
        Handler().postDelayed({
            paginationAdapter.removeLoadingFooter()
            isLoadingPage = false
            response?.results?.let { it1 -> paginationAdapter.addAll(it1) }

            if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter()
            else isLastPagePage = true
        },700)
    }

    private fun successLoadMovieFirst(response : MovieListResponse?){
        TOTAL_PAGES = response?.total_pages?:0
        response?.results?.let { it1 -> paginationAdapter.addAll(it1) }
        if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter()
        else isLastPagePage = true
        binding.btnCategory.isEnabled = true
        binding.loadingBarMovieList.visibility = View.GONE
    }

    private fun errorLoadMovie(message : String?){
        showToast(message?:getString(R.string.something_went_wrong))
        binding.loadingBarMovieList.visibility = View.GONE
    }
}