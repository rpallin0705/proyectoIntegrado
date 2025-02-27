package com.example.logincardview.domain.usecases

import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface
import kotlinx.coroutines.delay

class AddRestaurantUseCase(private val repository: RepositoryInterface<Restaurant>) {
    suspend operator fun invoke(restaurant: Restaurant) {
        repository.add(restaurant)
    }
}
