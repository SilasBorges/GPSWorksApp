package com.silas.gpsworksapp.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.silas.gpsworksapp.R
import com.silas.gpsworksapp.data.base.BaseAppCompatActivity
import com.silas.gpsworksapp.databinding.ActivityHomeBinding
import com.silas.gpsworksapp.ui.adapter.HomeAdapter
import com.silas.gpsworksapp.ui.adapter.listener.HomeListener
import com.silas.gpsworksapp.ui.viewmodel.HomeViewModel
import com.silas.gpsworksapp.util.MOVIE_ID
import com.silas.gpsworksapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeActivity : BaseAppCompatActivity(), HomeListener {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by inject()
    private val adapter: HomeAdapter by lazy { HomeAdapter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        setupAdapters()
        setupListeners()
        setupObservers()
    }

    private fun setupAdapters() {
        binding.homeList.adapter = adapter
    }

    private fun setupListeners() {
        binding.homeSearchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isEmpty()){
                    binding.homeTextSearch.visibility = View.VISIBLE
                    binding.homeTextSearch.text = getString(R.string.search_empty)
                    binding.homeList.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        binding.homeSearchClick.setOnClickListener {
            homeViewModel.getMovie(binding.homeSearchView.text.toString())
        }

        binding.homeFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun setupObservers() = homeViewModel.run {
        gpsWorkList.observe(this@HomeActivity) { singleLiveEvent ->
            singleLiveEvent.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let {
                            this@HomeActivity.lifecycleScope.launch {
                                lifecycleScope.launch {
                                    adapter.submitData(it)
                                    adapter.loadStateFlow.collectLatest {
                                    }
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
    }

    override fun clickDetails(id: String) {
        val intent = Intent(this@HomeActivity, DetailActivity::class.java).apply {
            putExtra(MOVIE_ID, id)
        }
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@HomeActivity).toBundle())
    }
}