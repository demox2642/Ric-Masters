package com.example.domain.usecase

import com.example.domain.repository.CamerasRepository

class GetCamerasListUseCase (private val camerasRepository: CamerasRepository) {
    suspend fun getCamerasList() = camerasRepository.getCamerasList()
}