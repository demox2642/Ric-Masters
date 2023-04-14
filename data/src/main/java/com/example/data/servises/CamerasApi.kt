package com.example.data.servises

import com.example.data.models.CamerasResponse
import retrofit2.http.GET

interface CamerasApi {
    @GET("api/rubetek/cameras/")
    suspend fun getCamerasList():CamerasResponse
}