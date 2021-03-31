package com.test.moviebox.view

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.test.moviebox.R
import com.test.moviebox.base.BaseActivity
import com.test.moviebox.databinding.ActivityFavoriteMovieListBinding
import com.test.moviebox.view.adapter.FavouriteMovieListAdapter
import com.test.moviebox.viewmodel.MovieRoomViewModel
import kotlinx.android.synthetic.main.partial_toolbar_with_back_icon.view.*

class FavoriteMovieListActivity : BaseActivity() {
    lateinit var roomViewModel : MovieRoomViewModel
    lateinit var binding : ActivityFavoriteMovieListBinding
    lateinit var adapter: FavouriteMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_movie_list)
        roomViewModel = ViewModelProviders.of(this).get(MovieRoomViewModel::class.java)
        binding.toolbar.toolbar_title.text = getString(R.string.favourite_movie)
        binding.toolbar.iv_arrow_back.setOnClickListener { finish() }
        setupAdapter()
        loadDataFromRoom()
    }

    private fun loadDataFromRoom(){
        roomViewModel.getMovieDetail(this)?.observe(this, Observer { movies ->
            if (movies.isEmpty()) showEmptyState()
            else adapter.setAdapterList(movies)
        })
    }

    private fun showEmptyState(){
        binding.let {
            it.rvFavMovieList.visibility = View.GONE
            it.tvLabelNotFound.visibility = View.VISIBLE
        }
    }

    private fun setupAdapter(){
        adapter = FavouriteMovieListAdapter(this)
        binding.let {
            it.rvFavMovieList.setHasFixedSize(true)
            it.rvFavMovieList.setItemViewCacheSize(20)
            adapter.setHasStableIds(true)
            adapter.onDetailClicked = {id ->
                startActivity(MovieDetailActivity.getStartIntent(this,id,true))
            }
            adapter.onDeleteClicked = { item ->
                roomViewModel.deleteData(this, item)
            }
            it.rvFavMovieList.adapter = adapter
        }
    }
}