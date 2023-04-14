package com.example.domain.usecase

import com.example.domain.repository.DoorsRepository

class RefreshDoorsDataUseCase(private val doorsRepository: DoorsRepository) {
    suspend fun refreshDoorData()=doorsRepository.refreshDoorData()
}