package com.example.domain.repository

import com.example.domain.models.CameraDomain
import kotlinx.coroutines.flow.Flow

interface CamerasRepository {
    suspend fun getCamerasList(): Flow<List<CameraDomain>>
}