package com.example.ricmasters.di // ktlint-disable package-name

import com.example.domain.repository.*
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun providesGetCamerasListUseCase(camerasRepository: CamerasRepository): GetCamerasListUseCase = GetCamerasListUseCase(camerasRepository)

    @Provides
    fun providesGetDoorsListUseCase(doorsRepository: DoorsRepository): GetDoorsListUseCase = GetDoorsListUseCase(doorsRepository)

}