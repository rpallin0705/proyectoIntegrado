package com.example.logincardview.data.repository

import com.example.logincardview.data.mappers.toDTO
import com.example.logincardview.data.mappers.toDomain
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface
import com.example.logincardview.network.RestaurantApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantRepository(
    private val apiService: RestaurantApiService
) : RepositoryInterface<Restaurant> {

    override suspend fun getAll(): List<Restaurant> {
        return try {
            val response = withContext(Dispatchers.IO) { apiService.getRestaurants() }
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { it.toDomain() }
            } else {
                println("Error en API")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error en API: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun add(o: Restaurant) {
        try {
            val response = withContext(Dispatchers.IO) { apiService.addRestaurant(o.toDTO()) }
            if (!response.isSuccessful) {
                println("No se pudo agregar a la API")
            }
        } catch (e: Exception) {
            println("Error en la API")
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            val response = withContext(Dispatchers.IO) { apiService.deleteRestaurant(id) }
            response.isSuccessful
        } catch (e: Exception) {
            println("Error en la API")
            false
        }
    }

    override suspend fun edit(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.editRestaurant(oldRestaurant.id, newRestaurant.toDTO())
            }
            if (!response.isSuccessful) {
                println("No se pudo editar en API")
            }
        } catch (e: Exception) {
            println("Error en la API")
        }
    }
}