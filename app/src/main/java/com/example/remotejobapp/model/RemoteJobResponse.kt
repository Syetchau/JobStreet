package com.example.remotejobapp.model

import com.google.gson.annotations.SerializedName

data class RemoteJobResponse(
    @SerializedName("job-count")
    val jobCount: Int?,
    val jobs: List<Job>?,
    @SerializedName("0-legal-notice")
    val legalNotice: String?
)