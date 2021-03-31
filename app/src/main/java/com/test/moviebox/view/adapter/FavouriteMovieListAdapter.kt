package com.test.moviebox.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import com.test.moviebox.databinding.ItemMovieListBinding
import com.test.moviebox.room.model.FavouriteMovieModel
import com.test.moviebox.utils.formatDateStyle1
import kotlinx.android.synthetic.main.item_movie_list.view.*
import java.lang.Exception

class FavouriteMovieListAdapter(var context: Context) : RecyclerView.Adapter<FavouriteMovieListAdapter.ViewHolder>(){
    var list = ArrayList<FavouriteMovieModel>()
    var onDetailClicked: (id: Int) -> Unit = { _ -> }
    var onDeleteClicked: (model : FavouriteMovieModel) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemMovieListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_movie_list,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]
        holder.bind(data)
        holder.itemView.btn_delete.setOnClickListener { onDeleteClicked(FavouriteMovieModel(
            data.movie_id,
            data.title,
            data.releaseDate,
            data.overview,
            data.posterPath
        )) }
        holder.itemView.container_item.setOnClickListener { onDetailClicked(data.movie_id) }
    }

    override fun getItemId(position: Int): Long {
        return list[position].movie_id.hashCode().toLong()
    }

    fun setAdapterList(list: List<FavouriteMovieModel>){
        this.list.clear()
        this.list = ArrayList(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : FavouriteMovieModel){
            binding.btnDelete.visibility = View.VISIBLE
            binding.tvTitle.text = data.title
            try {
                binding.tvReleaseDate.text = if (data.releaseDate.isNotEmpty()) formatDateStyle1(data.releaseDate) else "-"
            } catch (e : Exception){
                binding.tvReleaseDate.text = "-"
            }
            binding.tvOverview.text = data.overview
            data.posterPath?.let { poster ->
                Glide.with(binding.root.context)
                    .load("${BuildConfig.ENDPOINT_IMAGE_URL_w200}/$poster")
                    .placeholder(ContextCompat.getDrawable(binding.root.context, R.drawable.img_not_available))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageData)
            }
            binding.executePendingBindings()
        }
    }
}