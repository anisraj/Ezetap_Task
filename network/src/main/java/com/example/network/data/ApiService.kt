package com.example.network.data

import com.example.network.model.CustomUIResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/mobileapps/android_assignment")
    suspend fun fetchCustomUI(): Response<CustomUIResponse>

}