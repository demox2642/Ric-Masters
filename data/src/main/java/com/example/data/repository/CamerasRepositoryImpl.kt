package com.example.data.repository

import android.util.Log
import com.example.data.database.CameraDB
import com.example.data.servises.CamerasApi
import com.example.domain.models.CameraDomain
import com.example.domain.repository.CamerasRepository
import io.realm.Realm
import io.realm.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CamerasRepositoryImpl @Inject constructor(
    private val camerasApi: CamerasApi,
): CamerasRepository {
    override suspend fun getCamerasList(): Flow<List<CameraDomain>> {
        val camerasSize = Realm.getDefaultInstance()
            .where(CameraDB::class.java)
            .findAll()
            .size
        Log.e("CamerasRepo","camerasSize =$camerasSize")
        if (camerasSize == 0){
            updateCameraDBTable()
        }

        return    Realm.getDefaultInstance()
            .where(CameraDB::class.java)
            .findAll()
            .toFlow()
            .map { it.map(CameraDB::map) }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun setCameraFavorite(id: Int, favorites: Boolean) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            it.where(CameraDB::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.favorites = favorites
        }
        realm.close()
    }

    override suspend fun refreshCamerasData(): Flow<List<CameraDomain>> {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.delete(CameraDB::class.java)
        realm.commitTransaction()

        return getCamerasList()
    }

    private suspend fun updateCameraDBTable(){
        val response = camerasApi.getCamerasList()
        Log.e("CamerasRepo","response =$response")
        if (response.success) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            for (camera in response.data.cameras) {
                val cameraDB = realm.where(CameraDB::class.java)
                    .equalTo("id", camera.id).findFirst()
                if (cameraDB == null) {
                    val camRealm = realm.createObject(CameraDB::class.java, camera.id)
                    camRealm.name = camera.name
                    camRealm.room = camera.room
                    camRealm.favorites = camera.favorites
                    camRealm.snapshot = camera.snapshot
                    camRealm.rec = camera.rec
                } else {
                    cameraDB.name = camera.name
                    cameraDB.room = camera.room
                    cameraDB.favorites = camera.favorites
                    cameraDB.snapshot = camera.snapshot
                    cameraDB.rec = camera.rec
                }
            }
            realm.commitTransaction()
            realm.close()
        }
    }
}