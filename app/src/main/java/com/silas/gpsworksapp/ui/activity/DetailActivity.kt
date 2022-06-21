package com.silas.gpsworksapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.silas.gpsworksapp.data.base.BaseAppCompatActivity
import com.silas.gpsworksapp.databinding.ActivityDetailBinding
import com.silas.gpsworksapp.ui.viewmodel.DetailsViewModel
import com.silas.gpsworksapp.util.MOVIE_ID
import com.silas.gpsworksapp.util.Resource
import org.koin.android.ext.android.inject

class DetailActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailsViewModel: DetailsViewModel by inject()

    private val movieId by lazy { intent.extras?.getString(MOVIE_ID) }
    private var detailFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initExtra()
        setupObservers()
        setupListeners()
    }

    private fun initExtra() {
        movieId?.let { detailsViewModel.setupInit(it) }
    }

    private fun setupListeners() {
        binding.detailsBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() = detailsViewModel.run {
        detailResultLiveData.observe(this@DetailActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let { movieDetail ->

                            binding.detailsTitle.text = "${movieDetail.title} - ${movieDetail.year}"

                            Glide.with(binding.root)
                                .load(movieDetail.poster)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(binding.detailsImage)

                            binding.detailsFavorite.setOnClickListener {
                                if (detailFavorite) {
                                    movieId?.let { movieId ->
                                        detailsViewModel.removeMovieFavorite(
                                            movieId
                                        )
                                    }
                                } else {
                                    detailsViewModel.insertMovieFavorite(data)
                                }
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

        insertMovieFavorite.observe(this@DetailActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let {
                detailFavorite = true
                binding.detailsFavorite.speed = 1f
                binding.detailsFavorite.playAnimation()
            }
        }

        removeMovieFavorite.observe(this@DetailActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let {
                detailFavorite = false
                binding.detailsFavorite.progress = 0F
                binding.detailsFavorite.pauseAnimation()
            }
        }

        favoriteResultLiveData.observe(this@DetailActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            detailFavorite = true
                            binding.detailsFavorite.speed = 1f
                            binding.detailsFavorite.playAnimation()
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
}