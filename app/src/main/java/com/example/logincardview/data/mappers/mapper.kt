package com.example.logincardview.data.mappers

import com.example.logincardview.data.models.RestaurantDTO
import com.example.logincardview.domain.models.Restaurant

fun RestaurantDTO.toDomain(): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        address = this.address,
        phone = this.phone,
        rating = this.rating,
        description = this.description,
        imageUri = this.imageUri
    )
}

fun Restaurant.toDTO(): RestaurantDTO {
    return RestaurantDTO(
        id = this.id,
        name = this.name,
        address = this.address,
        phone = this.phone,
        rating = this.rating,
        description = this.description,
        imageUri = this.imageUri
    )
}
