package com.example.data.models

data class DoorsData(
    val id: Int,
    val name: String,
    val snapshot: String?,
    val room: String?,
    val favorites: Boolean
)