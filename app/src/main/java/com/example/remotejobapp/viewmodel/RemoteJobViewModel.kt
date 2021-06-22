package com.example.remotejobapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotejobapp.model.FavouriteJob
import com.example.remotejobapp.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(app: Application, private val repo: RemoteJobRepository): AndroidViewModel(app) {

    fun remoteJobResult() = repo.remoteJobResult()

    fun addFavouriteJob(job: FavouriteJob) = viewModelScope.launch {
        repo.addFavouriteJob(job)
    }

    fun deleteFavouriteJob(job: FavouriteJob) = viewModelScope.launch {
        repo.deleteFavouriteJob(job)
    }

    fun getAllFavouriteJob() = repo.getAllFavouriteJob()

    fun searchRemoteJob(query: String) = repo.searchJob(query)

    fun getSearchRemoteJobResult() = repo.searchJobResult()
}