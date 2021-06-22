package com.example.remotejobapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remotejobapp.api.RetrofitInstance
import com.example.remotejobapp.db.AppDatabase
import com.example.remotejobapp.model.FavouriteJob
import com.example.remotejobapp.model.RemoteJobResponse
import com.example.remotejobapp.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db: AppDatabase) {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseData: MutableLiveData<RemoteJobResponse> = MutableLiveData()
    private val searchJobResponseData: MutableLiveData<RemoteJobResponse> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {

        remoteJobService.getRemoteJobResponse().enqueue(object: Callback<RemoteJobResponse>{
            override fun onResponse(call: Call<RemoteJobResponse>, response: Response<RemoteJobResponse>) {
                if(response.body() != null) {
                    remoteJobResponseData.postValue(response.body())
                } else {
                    Log.d("Call api error", ""+response.code())
                }
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                remoteJobResponseData.postValue(null)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchJob(query: String?) {
        
        remoteJobService.searchJob(query).enqueue(object: Callback<RemoteJobResponse>{
            override fun onResponse(
                call: Call<RemoteJobResponse>,
                response: Response<RemoteJobResponse>
            ) {
                searchJobResponseData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                searchJobResponseData.postValue(null)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun remoteJobResult(): LiveData<RemoteJobResponse> {
        return remoteJobResponseData
    }

    fun searchJobResult(): LiveData<RemoteJobResponse> {
        return searchJobResponseData
    }

    suspend fun addFavouriteJob(job: FavouriteJob) = db.getFavouriteJobDao().addFavouriteJob(job)

    suspend fun deleteFavouriteJob(job: FavouriteJob) = db.getFavouriteJobDao().deleteFavJob(job)

    fun getAllFavouriteJob() = db.getFavouriteJobDao().getAllFavouriteJob()
}