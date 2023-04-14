package com.example.ricmasters.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CameraDomain
import com.example.domain.models.DoorsDomain
import com.example.domain.usecase.*
import com.example.ricmasters.ui.screens.models.EditDoor
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
    private val _revealedCamera = MutableLiveData<List<Int>> (listOf())
    val revealedCamera: LiveData<List<Int>>  = _revealedCamera

    private val _revealedDoors= MutableLiveData<List<Int>> (listOf())
    val revealedDoors: LiveData<List<Int>>  = _revealedDoors

    private val _showEditDialog = MutableLiveData<EditDoor?>(null)
    val showEditDialog: LiveData<EditDoor?> = _showEditDialog

    init {
        viewModelScope.launch {
            getDoorsListUseCase.getDoorsList().collectLatest {
                Log.e("MainViewModel","doors = $it")
                _doors.postValue(it)
            }

        }
        viewModelScope.launch {
            getCamerasListUseCase.getCamerasList().collectLatest {
                Log.e("MainViewModel","Cameras = $it")
                _cameras.postValue(it)
            }
        }

    }

    fun setDoorFavorite(id: Int, favorites: Boolean){
        viewModelScope.launch {
            setDoorFavoriteUseCase.setDoorFavorite(id, favorites)
        }
    }

    fun changeDoorName(editDoor: EditDoor){
        viewModelScope.launch {
            changeDoorNameUseCase.changeDoorName(editDoor.doorId, editDoor.name)
            _showEditDialog.postValue(null)
        }
    }

    fun setCameraFavorite(id: Int, favorites: Boolean){
        viewModelScope.launch {
            setCameraFavoriteUseCase.setCameraFavorite(id, favorites )
        }
    }

    fun onCameraItemExpanded(cameraId: Int) {
        if (_revealedCamera.value!!.isEmpty()) {
            _revealedCamera.value = _revealedCamera.value!!.toMutableList().also { list ->
                if (list.size > 0)
                    list.clear()
                list.add(cameraId)
            }
        }

    }

    fun onCameraItemCollapsed(cameraId: Int) {
        if (_revealedCamera.value!!.contains(cameraId) && !_revealedCamera.value!!.isEmpty()){
            _revealedCamera.value = _revealedCamera.value!!.toMutableList().also { list ->
                list.remove(cameraId)
            }
        }

    }

    fun doorsRefreshData(){

    }

    fun camerasRefreshData(){

    }
    fun allDoorsCollapse(){
        _revealedDoors.value = _revealedDoors.value!!.toMutableList().also { list ->
            list.clear()
        }
    }

    fun allCamerasCollapse(){
        _revealedCamera.value = _revealedCamera.value!!.toMutableList().also { list ->
            list.clear()
        }
    }

    fun onDoorsItemExpanded(cameraId: Int) {
        if (_revealedDoors.value!!.isEmpty()) {
            _revealedDoors.value = _revealedDoors.value!!.toMutableList().also { list ->
                if (list.size > 0)
                    list.clear()
                list.add(cameraId)
            }
        }

    }

    fun onDoorsItemCollapsed(cameraId: Int) {
        if (_revealedDoors.value!!.contains(cameraId) && !_revealedDoors.value!!.isEmpty()){
            _revealedDoors.value = _revealedDoors.value!!.toMutableList().also { list ->
                list.remove(cameraId)
            }
        }

    }

    fun showEditDialog(editDoor: EditDoor){
        _showEditDialog.postValue(editDoor)

    }

    fun closeDialog(){
        _showEditDialog.postValue(null)
    }
}