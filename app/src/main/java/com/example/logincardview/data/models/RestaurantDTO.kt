package com.example.logincardview.data.models

data class RestaurantDTO(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val rating: Int,
    val description: String,
    var imageUri: String? = null
)
