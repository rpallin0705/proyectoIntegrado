package com.example.logincardview.network

import com.example.logincardview.data.models.RestaurantDTO
import retrofit2.Response
import retrofit2.http.*

interface RestaurantApiService {
    @GET("/restaurant")
    suspend fun getRestaurants(): Response<List<RestaurantDTO>>

    @POST("/restaurant")
    suspend fun addRestaurant(@Body restaurant: RestaurantDTO): Response<RestaurantDTO>

    @PATCH("/restaurant/{id}")
    suspend fun editRestaurant(@Path("id") id: Long, @Body restaurant: RestaurantDTO): Response<RestaurantDTO>

    @DELETE("/restaurant/{id}")
    suspend fun deleteRestaurant(@Path("id") id: Long): Response<Unit>
}
