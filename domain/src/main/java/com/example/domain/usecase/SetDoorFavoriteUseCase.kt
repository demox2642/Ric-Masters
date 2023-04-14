package com.example.domain.usecase

import com.example.domain.repository.DoorsRepository

class SetDoorFavoriteUseCase(private val doorsRepository: DoorsRepository) {
    suspend fun setDoorFavorite(id: Int, favorites: Boolean)= doorsRepository.setDoorFavorite(id, favorites)
}