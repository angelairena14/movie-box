package com.test.moviebox.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
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
import com.test.moviebox.view.dialog.CategoryBottomSheetFragment
import com.test.moviebox.viewmodel.MovieViewModel
import com.test.moviebox.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.partial_main_toolbar.view.*
import kotlin.math.abs
import kotlin.math.sign


class MovieListActivity : BaseActivity() {
    lateinit var binding: ActivityMovieListBinding
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieListAdapter: MovieListAdapter
    var page = 1
    var totalPage = 0
    var type =  ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        setupAdapter()
        initViewModel()
        loadData()
        setListener()
    }

    private fun setListener(){
        binding.let {
            it.toolbar.iv_nav_favorite.setOnClickListener {
                startActivity(Intent(this,FavoriteMovieListActivity::class.java))
            }
            it.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (page < totalPage) {
                            page++
                            loadData()
                        }
                    }
                }
            })

            it.btnCategory.setOnClickListener {
                val dialog = CategoryBottomSheetFragment.newInstance(type)
                dialog.onClicked = { category ->
                    this.type = category
                    movieListAdapter.list.clear()
                    page = 1
                    totalPage = 0
                    loadData()
                }
                dialog.show(supportFragmentManager,"CategoryBottomSheetFragment")
            }
        }
    }

    private fun setupAdapter(){
        movieListAdapter = MovieListAdapter(this)
        binding.let {
            it.rvMovieList.setHasFixedSize(true)
            it.rvMovieList.setItemViewCacheSize(20)
            movieListAdapter.setHasStableIds(true)
            movieListAdapter.onClicked = {id ->
                startActivity(MovieDetailActivity.getStartIntent(this,id,false))
            }
            it.rvMovieList.adapter = movieListAdapter
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