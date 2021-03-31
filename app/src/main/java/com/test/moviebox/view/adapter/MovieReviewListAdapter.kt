package com.test.moviebox.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebox.BR
import com.test.moviebox.BuildConfig
import com.test.moviebox.R
import com.test.moviebox.databinding.ItemMovieReviewBinding
import com.test.moviebox.model.ReviewResults
import com.test.moviebox.utils.formatDateStyle1
import java.lang.Exception
import java.text.SimpleDateFormat

class MovieReviewListAdapter(var context: Context) : RecyclerView.Adapter<MovieReviewListAdapter.ViewHolder>(){
    var list = ArrayList<ReviewResults>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemMovieReviewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_movie_review,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.hashCode().toLong()
    }

    fun setAdapterList(list: List<ReviewResults>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding : ItemMovieReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : ReviewResults){
            val date = if (data.updated_at.isNotEmpty()) data.updated_at.substring(0,9) else ""
            try {
                if (date.isNotEmpty()){
                    try {
                        binding.tvUpdatedAt.text = formatDateStyle1(date)
                    } catch (e : Exception){
                        binding.tvUpdatedAt.text = "-"
                    }
                } else binding.tvUpdatedAt.text = "-"
            } catch (e : Exception){
                binding.tvUpdatedAt.text = "-"
            }
            binding.setVariable(BR.reviewlist,data)
            binding.tvLabelRate.text = if (data.author_details.rating != null) "${data.author_details.rating}" else "-"
            data.author_details.avatar_path?.let { avatar ->
                Glide.with(binding.root.context)
                    .load("${BuildConfig.ENDPOINT_IMAGE_URL_w200}/$avatar")
                    .placeholder(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_circle_grey))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageData)
            }
            binding.executePendingBindings()
        }
    }
}