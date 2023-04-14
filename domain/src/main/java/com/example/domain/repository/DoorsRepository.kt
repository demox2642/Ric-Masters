package com.example.domain.repository

import com.example.domain.models.DoorsDomain
import kotlinx.coroutines.flow.Flow

interface DoorsRepository {
    suspend fun getDoorsList(): Flow<List<DoorsDomain>>
}