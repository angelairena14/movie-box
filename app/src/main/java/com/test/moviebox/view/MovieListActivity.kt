package com.test.moviebox.view

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.moviebox.R
import com.test.moviebox.base.BaseActivity
import com.test.moviebox.databinding.ActivityMovieListBinding
import com.test.moviebox.model.MovieListResponse
import com.test.moviebox.utils.Constant.MovieFilterCategory.NOW_PLAYING
import com.test.moviebox.utils.Constant.MovieFilterCategory.POPULAR
import com.test.moviebox.utils.Constant.MovieFilterCategory.TOP_RATED
import com.test.moviebox.utils.Constant.MovieFilterCategory.UPCOMING
import com.test.moviebox.utils.Status
import com.test.moviebox.view.adapter.MovieListAdapter
import com.test.moviebox.view.adapter.MovieListPaginationAdapter
import com.test.moviebox.view.dialog.CategoryBottomSheetFragment
import com.test.moviebox.viewmodel.MovieViewModel
import com.test.moviebox.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.partial_main_toolbar.view.*


class MovieListActivity : BaseActivity() {
    lateinit var binding: ActivityMovieListBinding
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieListAdapter: MovieListAdapter
    lateinit var paginationAdapter: MovieListPaginationAdapter
    var page = 1
    var totalPage = 0
    var type =  ""

    private var PAGE_START = 1
    private var isLoadingPage = false
    private var isLastPagePage = false
    private var TOTAL_PAGES = 5
    private var currentPage = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        setupAdapter()
        initViewModel()
//        loadData()
        setListener()
        loadFirstPage()
    }

    private fun loadFirstPage() {
        movieViewModel.fetchMovieList(currentPage).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val response = resource.data
                        TOTAL_PAGES = resource.data?.total_pages?:0
                        response?.results?.let { it1 -> paginationAdapter.addAll(it1) }
                        if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter()
                        else isLastPagePage = true
                    }
                    else -> {}
                }
            }
        })
    }

    private fun setListener(){
        binding.let {
            it.toolbar.iv_nav_favorite.setOnClickListener {
                startActivity(Intent(this,FavoriteMovieListActivity::class.java))
            }

            val linearLayoutManager = it.rvMovieList.layoutManager as LinearLayoutManager
            it.rvMovieList.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
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
//                val dialog = CategoryBottomSheetFragment.newInstance(type)
//                dialog.onClicked = { category ->
//                    this.type = category
//                    movieListAdapter.list.clear()
//                    page = 1
//                    totalPage = 0
//                    loadData()
//                }
//                dialog.show(supportFragmentManager,"CategoryBottomSheetFragment")
            }
        }
    }

    private fun loadNextPage() {
        movieViewModel.fetchMovieList(currentPage).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        paginationAdapter.removeLoadingFooter();
                        isLoadingPage = false
                        val response = resource.data
                        response?.results?.let { it1 -> paginationAdapter.addAll(it1) }

                        if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter()
                        else isLastPagePage = true

                    }
                    else -> {}
                }
            }
        })
    }

    private fun setupAdapter(){
        movieListAdapter = MovieListAdapter(this)
        paginationAdapter = MovieListPaginationAdapter(this)
        binding.let {
            it.rvMovieList.setHasFixedSize(true)
            it.rvMovieList.setItemViewCacheSize(20)
            movieListAdapter.setHasStableIds(true)
            it.rvMovieList.adapter = paginationAdapter
        }
    }

    private fun initViewModel(){
        val movieViewModelFactory = MovieViewModelFactory()
        movieViewModel = ViewModelProviders.of(this,movieViewModelFactory).get(
            MovieViewModel::class.java)
    }

    private fun loadData() {
        when(type){
            POPULAR -> {
                movieViewModel.fetchPopularMovies(page).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                val response = it.data
                                successLoadMovie(response)
                            }
                            Status.ERROR -> errorLoadMovie(it.message)
                            Status.LOADING -> binding.loadingBarMovieList.visibility = View.VISIBLE
                        }
                    }
                })
            }
            UPCOMING -> {
                movieViewModel.fetchUpComingMovie(page).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                val response = it.data
                                successLoadMovie(response)
                            }
                            Status.ERROR -> errorLoadMovie(it.message)
                            Status.LOADING -> binding.loadingBarMovieList.visibility = View.VISIBLE
                        }
                    }
                })
            }
            TOP_RATED -> {
                movieViewModel.fetchTopRatedMovie(page).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                val response = it.data
                                successLoadMovie(response)
                            }
                            Status.ERROR -> errorLoadMovie(it.message)
                            Status.LOADING -> binding.loadingBarMovieList.visibility = View.VISIBLE
                        }
                    }
                })
            }
            NOW_PLAYING -> {
                movieViewModel.fetchNowPlayingMovie(page).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                val response = it.data
                                successLoadMovie(response)
                            }
                            Status.ERROR -> errorLoadMovie(it.message)
                            Status.LOADING -> binding.loadingBarMovieList.visibility = View.VISIBLE
                        }
                    }
                })
            }
            else -> {
                movieViewModel.fetchMovieList(page).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                val response = it.data
                                successLoadMovie(response)
                            }
                            Status.ERROR -> errorLoadMovie(it.message)
                            Status.LOADING -> binding.loadingBarMovieList.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }

    private fun successLoadMovie(response : MovieListResponse?){
        totalPage = response?.total_pages ?:0
        movieListAdapter.setAdapterList(response?.results?: emptyList())
        binding.loadingBarMovieList.visibility = View.GONE
        binding.btnCategory.isEnabled = true
    }

    private fun errorLoadMovie(message : String?){
        showToast(message?:getString(R.string.something_went_wrong))
        binding.loadingBarMovieList.visibility = View.GONE
    }
}