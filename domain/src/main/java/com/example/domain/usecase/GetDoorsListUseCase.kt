package com.example.domain.usecase

import com.example.domain.repository.DoorsRepository

class GetDoorsListUseCase(private val doorsRepository: DoorsRepository) {
    suspend fun getDoorsList() = doorsRepository.getDoorsList()
}