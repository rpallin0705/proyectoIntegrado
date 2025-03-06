package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.domain.models.Restaurant

import com.example.logincardview.domain.repository.RepositoryInterface

class EditRestaurantUseCase (private val repository: RepositoryInterface<Restaurant>){
    suspend operator fun invoke(oldRestaurant: Restaurant, newRestaurant: Restaurant){
        repository.edit(oldRestaurant, newRestaurant)
    }
}