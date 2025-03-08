package com.example.logincardview.domain.models

data class Restaurant(
    val id: Long?,
    val name: String,
    val address: String,
    val phone: String,
    val rating: Int,
    val description: String,
    var imageUri: String? = null
)
