package com.example.domain.usecase

import com.example.domain.repository.CamerasRepository

class RefreshCamerasDataUseCase(private val camerasRepository: CamerasRepository) {
    suspend fun refreshCamerasData() = camerasRepository.refreshCamerasData()
}