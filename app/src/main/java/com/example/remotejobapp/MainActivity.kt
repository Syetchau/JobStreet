package com.example.remotejobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.remotejobapp.databinding.ActivityMainBinding
import com.example.remotejobapp.db.AppDatabase
import com.example.remotejobapp.repository.RemoteJobRepository
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import com.example.remotejobapp.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setupViewModel()
    }

    private fun setupViewModel() {
        val remoteJobRepo = RemoteJobRepository(AppDatabase(this))
        val viewModelProvideFactory = RemoteJobViewModelFactory(application, remoteJobRepo)
        viewModel = ViewModelProvider(this, viewModelProvideFactory)
            .get(RemoteJobViewModel::class.java)
    }
}