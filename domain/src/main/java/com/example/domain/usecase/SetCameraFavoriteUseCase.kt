package com.example.domain.usecase

import com.example.domain.repository.CamerasRepository

class SetCameraFavoriteUseCase(private val camerasRepository: CamerasRepository) {
    suspend fun setCameraFavorite(id: Int, favorites: Boolean)=camerasRepository.setCameraFavorite(id, favorites)
}