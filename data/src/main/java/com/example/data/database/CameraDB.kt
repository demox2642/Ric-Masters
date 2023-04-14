package com.example.data.database

import com.example.domain.models.CameraDomain
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CameraDB : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var snapshot: String = ""
    var room: String? = ""
    var favorites: Boolean = false
    var rec: Boolean = false

    fun map() = CameraDomain(id, name, snapshot, room, favorites, rec)
}