package com.example.logincardview.network

import com.example.logincardview.domain.models.Restaurant
import retrofit2.Response
import retrofit2.http.*

interface RestaurantApiService {
    @GET("/restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>

    @POST("/restaurants")
    suspend fun addRestaurant(@Body restaurant: Restaurant): Response<Restaurant>

    @PUT("/restaurants/{id}")
    suspend fun editRestaurant(@Path("id") id: Long, @Body restaurant: Restaurant): Response<Restaurant>

    @DELETE("/restaurants/{id}")
    suspend fun deleteRestaurant(@Path("id") id: Long): Response<Unit>
}
