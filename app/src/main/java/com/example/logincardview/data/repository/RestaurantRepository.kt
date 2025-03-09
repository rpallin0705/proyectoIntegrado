package com.example.logincardview.data.repository

import android.util.Log
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

    override suspend fun delete(id: Long): Boolean {
        return try {
            val response = withContext(Dispatchers.IO) { apiService.deleteRestaurant(id) }
            response.isSuccessful
        } catch (e: Exception) {
            println("Error en la API")
            false
        }
    }

    override suspend fun add(o: Restaurant): Restaurant? {
        return try {
            Log.d("RestaurantRepository", "Enviando POST con: $o")

            val response = withContext(Dispatchers.IO) {
                apiService.addRestaurant(o.toDTO())
            }

            if (response.isSuccessful && response.body() != null) {
                val addedRestaurant = response.body()!!.toDomain()
                Log.d("RestaurantRepository", "Restaurante añadido correctamente: $addedRestaurant")
                addedRestaurant
            } else {
                Log.e("RestaurantRepository", "Error en API: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Excepción en API: ${e.message}", e)
            null
        }
    }


    override suspend fun edit(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        try {
            Log.d(
                "RestaurantRepository",
                "Enviando PATCH con ID ${oldRestaurant.id} y datos $newRestaurant"
            )
            val response = withContext(Dispatchers.IO) {
                apiService.editRestaurant(oldRestaurant.id!!, newRestaurant.toDTO())
            }

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("RestaurantRepository", "Error en API: $errorBody")
            } else {
                Log.d("RestaurantRepository", "Restaurante editado con éxito")
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Excepción en API: ${e.localizedMessage}", e)
        }
    }

    override suspend fun getFavorites(): Set<Long> {
        return try {
            val response = apiService.getFavorites()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toSet()
            } else {
                emptySet()
            }
        } catch (e: Exception) {
            emptySet()
        }
    }

    override suspend fun toggleFavorite(restaurantId: Long): Set<Long> {
        return try {
            val response = apiService.toggleFavorite(restaurantId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toSet()
            } else {
                emptySet()
            }
        } catch (e: Exception) {
            emptySet()
        }
    }
}
