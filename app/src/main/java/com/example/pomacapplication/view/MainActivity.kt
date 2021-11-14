package com.example.pomacapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.pomacapplication.R
import com.example.pomacapplication.databinding.ActivityMainBinding
import com.example.pomacapplication.repository.NewsRepository
import com.example.pomacapplication.viewModel.NewsViewModel
import com.example.pomacapplication.viewModel.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    //vars
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //inflate the toolbar
        setSupportActionBar(binding.toolBar)
        //inflate the NavigationUI
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_movie) as NavHostFragment?
        NavigationUI.setupWithNavController(
            binding.toolBar,
            navHostFragment!!.navController)
        //inflate the viewModel
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}