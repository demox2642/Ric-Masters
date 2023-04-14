package com.example.data.servises

import com.example.data.models.DoorsResponse
import retrofit2.http.GET

interface DoorsApi {
@GET("api/rubetek/doors/")
    suspend fun getDoorsList(): DoorsResponse
}