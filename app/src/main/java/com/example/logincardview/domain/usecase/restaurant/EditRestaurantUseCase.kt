package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.data.repository.RestaurantRepository

class EditRestaurantUseCase(private val repository: RestaurantRepository) {
    suspend operator fun invoke(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        repository.edit(oldRestaurant, newRestaurant)
    }
}