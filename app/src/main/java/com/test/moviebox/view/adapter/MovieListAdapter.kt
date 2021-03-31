package com.test.moviebox.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.test.moviebox.BR
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import com.test.moviebox.databinding.ItemMovieListBinding
import com.test.moviebox.model.MovieListDetail
import com.test.moviebox.utils.formatDateStyle1

class MovieListAdapter(var context: Context) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>(){
    var list = ArrayList<MovieListDetail>()
    var onClicked: (id: Int) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemMovieListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_movie_list,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onClicked(list[position].id) }
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }

    fun setAdapterList(list: List<MovieListDetail>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : MovieListDetail){
            binding.tvTitle.text = data.title
            try {
                binding.tvReleaseDate.text = if (data.release_date.isNotEmpty()) formatDateStyle1(data.release_date) else "-"
            } catch (e :Exception){
                binding.tvReleaseDate.text = "-"
            }
            binding.tvOverview.text = data.overview
            data.poster_path?.let { poster ->
                Glide.with(binding.root.context)
                    .load("${BuildConfig.ENDPOINT_IMAGE_URL_w200}/${poster}")
                    .placeholder(ContextCompat.getDrawable(binding.root.context,R.drawable.img_not_available))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageData)
            }
            binding.executePendingBindings()
        }
    }
}