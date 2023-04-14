package com.example.domain.models

data class DoorsDomain(
    val id: Int,
    val name: String,
    val snapshot: String?,
    val room: String?,
    val favorites: Boolean
)
