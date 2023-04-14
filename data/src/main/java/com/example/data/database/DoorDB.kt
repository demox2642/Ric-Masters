package com.example.data.database

import com.example.domain.models.DoorsDomain
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class DoorDB : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var snapshot: String? = null
    var room: String? = null
    var favorites: Boolean = false

    fun map() = DoorsDomain(id, name, snapshot, room, favorites)
}