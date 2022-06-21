package com.silas.gpsworksapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.silas.gpsworksapp.data.response.Work
import com.silas.gpsworksapp.databinding.ListItemMovieBinding
import com.silas.gpsworksapp.ui.adapter.listener.FavoriteListener

class FavoriteAdapter(
    var context: Context,
    val favoriteListener: FavoriteListener
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var adapterList: MutableList<Work>? = null

    fun setList(list: List<Work>) {
        this.adapterList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterList?.get(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(work: Work) {
            binding.apply {
                listItemMovieTitle.text =
                    "${work.title} (${work.year})"

                Glide.with(root)
                    .load(work.poster)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(listItemMovieImage)

                listItemMovieCardView.setOnClickListener {
                    work.imdbID.let { favoriteListener.clickDetails(it) }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList?.size ?: 0
    }
}