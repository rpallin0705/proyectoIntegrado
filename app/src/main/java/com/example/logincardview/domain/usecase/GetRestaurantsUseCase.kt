package com.example.logincardview.domain.usecase

import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface

class GetRestaurantsUseCase(private val restaurantRepository: RepositoryInterface<Restaurant>) {
    suspend fun execute(): List<Restaurant> {
        return restaurantRepository.getAll()
    }
}