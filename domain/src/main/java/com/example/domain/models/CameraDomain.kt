package com.example.domain.models

data class CameraDomain(
    val id: Int,
    val name: String,
    val snapshot: String,
    var room: String,
    val favorites: Boolean,
    val rec: Boolean
)
