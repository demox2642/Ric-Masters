package com.example.data.models

data class Camera(
    val id: Int,
    val name: String,
    val snapshot: String,
    var room: String,
    val favorites: Boolean,
    val rec: Boolean
)