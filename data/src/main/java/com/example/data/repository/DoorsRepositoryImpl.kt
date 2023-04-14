package com.example.data.repository

import com.example.data.database.DoorDB
import com.example.data.servises.DoorsApi
import com.example.domain.models.DoorsDomain
import com.example.domain.repository.DoorsRepository
import io.realm.Realm
import io.realm.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DoorsRepositoryImpl @Inject constructor(
    private val doorsApi: DoorsApi
):DoorsRepository {
    override suspend fun getDoorsList(): Flow<List<DoorsDomain>> {
    val doorsSize = Realm.getDefaultInstance()
            .where(DoorDB::class.java)
            .findAll()
        .size

        if (doorsSize == 0){
            updateDoorsDBTable()
        }

       return    Realm.getDefaultInstance()
            .where(DoorDB::class.java)
            .findAll()
            .toFlow()
            .map { it.map(DoorDB::map) }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun setDoorFavorite(id: Int, favorites: Boolean) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            it.where(DoorDB::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.favorites = favorites
        }
        realm.close()
    }

    override suspend fun changeDoorName(id: Int, name: String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            it.where(DoorDB::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.name = name
        }
        realm.close()
    }

    override suspend fun refreshDoorData(): Flow<List<DoorsDomain>> {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.delete(DoorDB::class.java)
        realm.commitTransaction()

        return getDoorsList()
    }

    private suspend fun updateDoorsDBTable(){
        val response = doorsApi.getDoorsList()
        if (response.success) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            for (door in response.data) {
                val d = realm.where(DoorDB::class.java)
                    .equalTo("id", door.id).findFirst()
                if (d == null) {
                    val doorRealm = realm.createObject(DoorDB::class.java, door.id)
                    doorRealm.name = door.name
                    doorRealm.room = door.room
                    doorRealm.favorites = door.favorites
                    doorRealm.snapshot = door.snapshot
                } else {
                    d.name = door.name
                    d.room = door.room
                    d.favorites = door.favorites
                    d.snapshot = door.snapshot
                }
            }
            realm.commitTransaction()
            realm.close()
        }
    }
}