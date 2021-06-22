package com.example.remotejobapp.api

import com.example.remotejobapp.model.RemoteJobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("remote-jobs")
    fun getRemoteJobResponse() : Call<RemoteJobResponse>

    @GET("remote-jobs")
    fun searchJob(@Query("search") query: String? ): Call<RemoteJobResponse>
}