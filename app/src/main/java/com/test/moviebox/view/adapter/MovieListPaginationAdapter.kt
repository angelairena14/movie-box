package com.test.moviebox.view.adapter

import android.content.Context
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.test.moviebox.R
import com.test.moviebox.model.MovieListDetail


class MovieListPaginationAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list = ArrayList<MovieListDetail?>()
    val LOADING = 0
    val ITEM = 1
    var isLoadingAdded = false

    fun setMovieList(list: ArrayList<MovieListDetail?>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View =
                    inflater.inflate(R.layout.item_movie_list, parent, false)
                viewHolder = MovieViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }


    override fun getItemCount(): Int {
        return if (list == null) 0 else list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position]?.id.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else ITEM
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(null)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = list.size - 1
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun add(movie: MovieListDetail?) {
        list.add(movie)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<MovieListDetail?>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getItem(position : Int) : MovieListDetail? {
        return list[position]
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.tv_title)
        val movieImage: AppCompatImageView = itemView.findViewById(R.id.image_data)
    }

    class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.loadmore_progress) as ProgressBar

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = list[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieViewHolder = holder as MovieViewHolder
                movieViewHolder.movieTitle.text = movie?.title
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }

    }
}