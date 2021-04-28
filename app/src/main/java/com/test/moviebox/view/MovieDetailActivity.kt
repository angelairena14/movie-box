package com.test.moviebox.view

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import com.test.moviebox.base.BaseActivity
import com.test.moviebox.databinding.ActivityMovieDetailBinding
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.model.MovieReviewResponse
import com.test.moviebox.room.model.FavouriteMovieModel
import com.test.moviebox.utils.Constant.IntentKey.IS_FAVOURITE
import com.test.moviebox.utils.Constant.IntentKey.MOVIE_ID
import com.test.moviebox.utils.NewResource
import com.test.moviebox.utils.formatDateStyle1
import com.test.moviebox.view.adapter.MovieReviewListAdapter
import com.test.moviebox.view.dialog.PosterPreviewDialog
import com.test.moviebox.viewmodel.MovieRoomViewModel
import com.test.moviebox.viewmodel.MovieViewModel
import com.test.moviebox.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.partial_toolbar_with_back_icon.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class MovieDetailActivity : BaseActivity() {
    companion object {
        fun getStartIntent(
            context: Context?,
            movieId : Int,
            isFavourite : Boolean
        ): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID,movieId)
            intent.putExtra(IS_FAVOURITE,isFavourite)
            return intent
        }
    }
    lateinit var binding : ActivityMovieDetailBinding
    lateinit var movieViewModel: MovieViewModel
    lateinit var roomViewModel : MovieRoomViewModel
    private var movieId = 0
    private var isFavourite = false
    private var saveMovieId = 0
    private var saveMovieTitle = ""
    private var saveReleaseDate = ""
    private var saveOverView = ""
    private var savePoster : String? = null
    private var page = 1
    private var totalPage = 1
    lateinit var movieReviewAdapter: MovieReviewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        movieId = intent.getIntExtra(MOVIE_ID,0)
        isFavourite = intent.getBooleanExtra(IS_FAVOURITE,false)
        if (isFavourite) {
            binding.ivFavorite.visibility = View.GONE
        } else {
            binding.ivFavorite.visibility = View.VISIBLE
            binding.ivFavorite.isClickable = false
        }
        initViewModel()
        setupAdapter()
        loadFirstData()
        setListener()
        AsyncTask.execute {
            if (getDataExists(movieId) == 1) setIsFavourite()
        }
    }

    private fun setupAdapter(){
        movieReviewAdapter = MovieReviewListAdapter(this)
        binding.let {
            it.rvRating.setHasFixedSize(true)
            it.rvRating.setItemViewCacheSize(20)
            movieReviewAdapter.setHasStableIds(true)
            it.rvRating.adapter = movieReviewAdapter
        }
    }

    private fun initViewModel(){
        val movieViewModelFactory = MovieViewModelFactory()
        movieViewModel = ViewModelProviders.of(this,movieViewModelFactory).get(MovieViewModel::class.java)
        roomViewModel = ViewModelProviders.of(this).get(MovieRoomViewModel::class.java)
    }

    private fun setListener(){
        binding.let {
            it.toolbarDetail.iv_arrow_back.setOnClickListener { finish() }
            it.nestedScrollDetail.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                    if (!it.nestedScrollDetail.canScrollVertically(1)) {
                        if (page < totalPage) {
                            page++
                            lifecycleScope.launch {
                                movieViewModel.fetchMovieRatingDeffered(movieId,page)
                            }
                        }
                    }
                }
            })
            it.ivFavorite.setOnClickListener { _ ->
                roomViewModel.insertData(this, FavouriteMovieModel(
                    saveMovieId,
                    saveMovieTitle,
                    saveReleaseDate,
                    saveOverView,
                    savePoster
                ))
                showToast(getString(R.string.added_to_favourite))
                setIsFavourite()
            }

            it.tvSeePoster.setOnClickListener {
                PosterPreviewDialog.newInstance(savePoster).show(supportFragmentManager,"PosterPreviewDialog")
            }
        }
    }

    private fun setIsFavourite(){
        binding.let {
            it.ivFavorite.isEnabled = false
            it.ivFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_heart_red))
        }
    }

    private fun getDataExists(movieId: Int) : Int?{
        return roomViewModel.getMovieSingle(this,movieId)
    }

    private fun loadFirstData(){
        binding.let {bind ->
            movieViewModel.loadData(movieId,page)
            loadMovieDetail()
            loadMovieReview()
        }
    }

    fun loadMovieDetail(){
        movieViewModel.movieDetail.observe(this, Observer { response ->
            when (response) {
                is NewResource.Success -> {
                    response.data?.let { picsResponse ->
                        successLoadData(picsResponse)
                    }
                }

                is NewResource.Error -> {
                    Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show()
                }

                is NewResource.Loading -> {
                    binding.loadingBarMovieDetail.visibility = View.VISIBLE
                }
            }
        })
    }

    fun loadMovieReview() {
        movieViewModel.movieRating.observe(this, Observer { response ->
            when (response) {
                is NewResource.Success -> {
                    response.data?.let { picsResponse ->
                        if (picsResponse.total_results == 0) showEmptyReview()
                        else successLoadReview(picsResponse)
                    }
                }

                is NewResource.Error -> {
                    Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show()
                }

                is NewResource.Loading -> {
                    binding.loadingBarMovieDetail.visibility = View.VISIBLE
                }
            }
        })
    }

    fun successLoadData(detail: MovieListDetail?){
        Log.i("data_is","called movie")
        val context = this@MovieDetailActivity
        var title = ""
        val charCount = 25
        binding.let { bind ->
            saveMovieId = detail?.id?:0
            saveMovieTitle = detail?.title?:""
            saveReleaseDate = detail?.release_date?:""
            saveOverView = detail?.overview?:""
            savePoster = detail?.poster_path?:""
            bind.moviedetail = detail
            bind.ivFavorite.isClickable = true
            title = if (saveMovieTitle.length < charCount) saveMovieTitle
            else "${detail?.title?.substring(0,charCount)}..."
            bind.toolbarDetail.toolbar_title.text = title
            bind.tvLabelOverview.text = getString(R.string.overview)
            bind.tvSeePoster.visibility = View.VISIBLE
            try {
                bind.tvReleaseDate.text = if (detail?.release_date?.isNotEmpty() == true){
                    formatDateStyle1(detail.release_date ?:"")
                } else "-"
            } catch (e : Exception){
                bind.tvReleaseDate.text = "-"
            }
            savePoster?.let { poster ->
                Glide.with(context)
                    .load("${BuildConfig.ENDPOINT_IMAGE_URL_w200}/${poster}")
                    .placeholder(ContextCompat.getDrawable(context,R.drawable.img_not_available))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(bind.imageData)
            }
            bind.loadingBarMovieDetail.visibility = View.GONE
        }
    }

    private fun showEmptyReview(){
        binding.let {
            it.loadingBarMovieDetail.visibility = View.GONE
            it.rvRating.visibility = View.GONE
            it.layoutEmptyReview.visibility = View.VISIBLE
        }
    }

    private fun successLoadReview(response : MovieReviewResponse?){
        Log.i("data_is","called rating")
        totalPage = response?.total_pages ?:0
        movieReviewAdapter.setAdapterList(response?.results?: emptyList())
        binding.loadingBarMovieDetail.visibility = View.GONE
    }

    private fun setError(message : String?){
        showToast(message?:getString(R.string.something_went_wrong))
        binding.loadingBarMovieDetail.visibility = View.GONE
    }
}