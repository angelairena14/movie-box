package com.test.moviebox.view.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.utils.formatDateStyle1


class MovieListPaginationAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = ArrayList<MovieListDetail?>()
    val LOADING = 0
    val ITEM = 1
    var isLoadingAdded = false
    var onClicked: (id: Int) -> Unit = { _ -> }
    var onListened : (pos : Int) -> Unit = {_ -> }

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
        notifyItemInserted(list.size)
    }


    fun addAll(movieResult: ArrayList<MovieListDetail?>) {
        val oldList = movieResult
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MovieListDetailCallback(
                oldList,
                movieResult
            )
        )
        this.list.addAll(movieResult)
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.tv_title)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)
        val movieImage: AppCompatImageView = itemView.findViewById(R.id.image_data)
        val movieDescription: TextView = itemView.findViewById(R.id.tv_overview)
    }

    class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val layoutProgressBar: ConstraintLayout =
            itemView.findViewById(R.id.layout_loading) as ConstraintLayout

    }

    class MovieListDetailCallback(
        var oldMovieList: ArrayList<MovieListDetail?>,
        var newMovieList: ArrayList<MovieListDetail?>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMovieList[oldItemPosition]?.id == newMovieList[newItemPosition]?.id)
        }

        override fun getOldListSize(): Int {
            return oldMovieList.size
        }

        override fun getNewListSize(): Int {
            return newMovieList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMovieList[oldItemPosition]?.equals(newMovieList[newItemPosition])?:false
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = list[position]
        when (getItemViewType(position)) {
            ITEM -> {
                Handler().postDelayed({
                    onListened(holder.adapterPosition)
                },1200)
                val movieViewHolder = holder as MovieViewHolder
                try {
                    movieViewHolder.movieReleaseDate.text =
                        if (movie?.release_date?.isNotEmpty() == true)
                            formatDateStyle1(movie.release_date) else "-"
                } catch (e: Exception) {
                    movieViewHolder.movieReleaseDate.text = "-"
                }
                movieViewHolder.movieTitle.text = movie?.title
                movieViewHolder.movieDescription.text = movie?.overview
                movie?.poster_path?.let { poster ->
                    Glide.with(context)
                        .load("${BuildConfig.ENDPOINT_IMAGE_URL_w200}/${poster}")
                        .placeholder(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.img_not_available
                            )
                        )
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(movieViewHolder.movieImage)
                }
                holder.itemView.setOnClickListener { onClicked(list[position]?.id ?: 0) }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.layoutProgressBar.visibility = View.VISIBLE
            }
        }

    }
}