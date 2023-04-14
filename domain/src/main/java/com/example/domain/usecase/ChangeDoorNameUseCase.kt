package com.example.domain.usecase

import com.example.domain.repository.DoorsRepository

class ChangeDoorNameUseCase(private val doorsRepository: DoorsRepository) {
    suspend fun changeDoorName(id: Int, name: String) = doorsRepository.changeDoorName(id, name)
}