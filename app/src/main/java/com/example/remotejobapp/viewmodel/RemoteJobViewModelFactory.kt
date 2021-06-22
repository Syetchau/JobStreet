package com.example.remotejobapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remotejobapp.repository.RemoteJobRepository

class RemoteJobViewModelFactory(val app: Application, private val repo: RemoteJobRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(app, repo) as T
    }
}