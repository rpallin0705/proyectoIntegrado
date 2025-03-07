package com.example.logincardview.data.repository

import com.example.logincardview.data.mappers.toDTO
import com.example.logincardview.data.mappers.toDomain
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface
import com.example.logincardview.network.RestaurantApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RestaurantRepository(
    private val apiService: RestaurantApiService,
    private val inMemoryRepository: RestaurantInMemoryRepository
) : RepositoryInterface<Restaurant> {

    override suspend fun getAll(): List<Restaurant> {
        return try {
            val response = withContext(Dispatchers.IO) { apiService.getRestaurants() }

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { it.toDomain() } // Convertir DTO a Modelo de Dominio
            } else {
                println("Error en API, usando fallback")
                inMemoryRepository.getAll() // Fallback a datos en memoria
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                println("Error en API: ${e.message()}")
            } else {
                println("Error desconocido: ${e.localizedMessage}")
            }
            inMemoryRepository.getAll() // Fallback a datos en memoria
        }
    }

    override suspend fun add(o: Restaurant) {
        try {
            val response = withContext(Dispatchers.IO) { apiService.addRestaurant(o.toDTO()) }
            if (!response.isSuccessful) {
                println("No se pudo agregar a la API, guardando localmente.")
                inMemoryRepository.add(o)
            }
        } catch (e: Exception) {
            println("Error en la API, guardando localmente.")
            inMemoryRepository.add(o)
        }
    }

    override suspend fun delete(id: Long): Boolean { // Cambiar Int a Long
        return try {
            val response = withContext(Dispatchers.IO) { apiService.deleteRestaurant(id) }
            if (response.isSuccessful) {
                true
            } else {
                println("No se pudo eliminar en API, eliminando localmente.")
                inMemoryRepository.delete(id)
            }
        } catch (e: Exception) {
            println("Error en la API, eliminando localmente.")
            inMemoryRepository.delete(id)
        }
    }

    override suspend fun edit(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.editRestaurant(oldRestaurant.id, newRestaurant.toDTO()) // Aquí ya es Long
            }
            if (!response.isSuccessful) {
                println("No se pudo editar en API, editando localmente.")
                inMemoryRepository.edit(oldRestaurant, newRestaurant)
            }
        } catch (e: Exception) {
            println("Error en la API, editando localmente.")
            inMemoryRepository.edit(oldRestaurant, newRestaurant)
        }
    }
}
