package com.example.domain.repository

import com.example.domain.models.DoorsDomain
import kotlinx.coroutines.flow.Flow

interface DoorsRepository {
    suspend fun getDoorsList(): Flow<List<DoorsDomain>>
    suspend fun setDoorFavorite(id: Int, favorites: Boolean)
    suspend fun changeDoorName(id: Int, name: String)
    suspend fun refreshDoorData(): Flow<List<DoorsDomain>>
}