package com.silas.gpsworksapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.silas.gpsworksapp.data.response.Search
import com.silas.gpsworksapp.databinding.ListItemMovieBinding
import com.silas.gpsworksapp.ui.adapter.listener.HomeListener
import com.silas.gpsworksapp.util.NETWORK_VIEW_TYPE
import com.silas.gpsworksapp.util.PRODUCT_VIEW_TYPE

class HomeAdapter(val context: Context, val homeListener: HomeListener) :
    PagingDataAdapter<Search, HomeAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(search: Search) {
            binding.apply {
                listItemMovieTitle.text =
                    "${search.title} (${search.year})"

                Glide.with(root)
                    .load(search.poster)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(listItemMovieImage)

                listItemMovieCardView.setOnClickListener {
                    search.imdbID?.let { homeListener.clickDetails(it) }
                }
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(
            oldItem: Search,
            newItem: Search
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Search,
            newItem: Search
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            NETWORK_VIEW_TYPE
        } else {
            PRODUCT_VIEW_TYPE
        }
    }
}