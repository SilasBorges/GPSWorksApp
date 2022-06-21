package com.silas.gpsworksapp.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.silas.gpsworksapp.data.base.BaseAppCompatActivity
import com.silas.gpsworksapp.databinding.ActivityFavoriteBinding
import com.silas.gpsworksapp.ui.adapter.FavoriteAdapter
import com.silas.gpsworksapp.ui.adapter.listener.FavoriteListener
import com.silas.gpsworksapp.ui.viewmodel.FavoriteViewModel
import com.silas.gpsworksapp.util.MOVIE_ID
import com.silas.gpsworksapp.util.Resource
import org.koin.android.ext.android.inject

class FavoriteActivity : BaseAppCompatActivity(), FavoriteListener {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by inject()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setupAdapters()
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getMovieFavorite()
    }

    private fun setupAdapters() {
        binding.favoriteList.adapter = favoriteAdapter
    }

    private fun setupListeners() {
        binding.favoriteBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObservers() = favoriteViewModel.run {
        favoriteResultLiveData.observe(this@FavoriteActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let { listMovieResult ->
                            if(listMovieResult.isNotEmpty()){
                                favoriteAdapter.setList(listMovieResult)
                            }
                        }
                        dismissProgressDialog()
                    }
                    Resource.Status.LOADING -> {
                        showProgressDialog()
                    }
                    Resource.Status.ERROR -> {
                        dismissProgressDialog()
                    }
                }
            }
        }
    }

    override fun clickDetails(id: String) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
            putExtra(MOVIE_ID, id)
        }
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@FavoriteActivity).toBundle())
    }
}