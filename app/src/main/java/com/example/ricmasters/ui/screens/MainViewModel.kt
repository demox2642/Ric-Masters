package com.example.ricmasters.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CameraDomain
import com.example.domain.models.DoorsDomain
import com.example.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDoorsListUseCase: GetDoorsListUseCase,
    private val getCamerasListUseCase: GetCamerasListUseCase,
    private val setDoorFavoriteUseCase: SetDoorFavoriteUseCase,
    private val changeDoorNameUseCase: ChangeDoorNameUseCase,
    private val setCameraFavoriteUseCase: SetCameraFavoriteUseCase
):ViewModel () {
    private val _doors = MutableLiveData<List<DoorsDomain>>()
    val doors:LiveData<List<DoorsDomain>> = _doors
    private val _cameras = MutableLiveData<List<CameraDomain>>()
    val cameras:LiveData<List<CameraDomain>> = _cameras

    init {
        viewModelScope.launch {
            getDoorsListUseCase.getDoorsList().collectLatest {
                _doors.postValue(it)
            }
            getCamerasListUseCase.getCamerasList().collectLatest {
                _cameras.postValue(it)
            }
        }

    }

    fun setDoorFavorite(id: Int, favorites: Boolean){
        viewModelScope.launch {
            setDoorFavoriteUseCase.setDoorFavorite(id, favorites)
        }
    }

    fun changeDoorName(id: Int, name: String){
        viewModelScope.launch {
            changeDoorNameUseCase.changeDoorName(id, name)
        }
    }

    fun setCameraFavorite(id: Int, favorites: Boolean){
        viewModelScope.launch {
            setCameraFavoriteUseCase.setCameraFavorite(id, favorites )
        }
    }
}