package com.example.logincardview.data.repository

import com.example.logincardview.data.service.RestaurantService
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.models.RestaurantData
import com.example.logincardview.domain.repository.RestaurantRepositoryInterface

class InMemoryRestaurantRepository : RestaurantRepositoryInterface<Restaurant> {

    private val restaurantService : RestaurantService = RestaurantService()

    override suspend fun getAll(): List<Restaurant> {
        var mutableRestaurants : MutableList<Restaurant> = mutableListOf()
        val datasource = restaurantService.getRestaurants()
        RestaurantData.restaurants = mutableRestaurants
        return RestaurantData.restaurants
    }
}