package com.example.logincardview.ui.modelview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.data.repository.RestaurantRepository
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.usecase.restaurant.AddRestaurantUseCase
import com.example.logincardview.domain.usecase.restaurant.DeleteRestaurantUseCase
import com.example.logincardview.domain.usecase.restaurant.EditRestaurantUseCase
import com.example.logincardview.domain.usecase.restaurant.GetRestaurantsUseCase
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repository: RestaurantRepository) : ViewModel() {

    val restaurantLiveData = MutableLiveData<List<Restaurant>>()
    private val progressBarLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<String>()
    val favoritesLiveData = MutableLiveData<Set<Long>>()

    private val getRestaurantsUseCase = GetRestaurantsUseCase(repository)
    private val addRestaurantUseCase = AddRestaurantUseCase(repository)
    private val deleteRestaurantUseCase = DeleteRestaurantUseCase(repository)
    private val editRestaurantUseCase = EditRestaurantUseCase(repository)

    fun getRestaurants() {
        viewModelScope.launch {
            val data = repository.getAll()
            restaurantLiveData.postValue(data)
        }
    }

    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            val favoriteIds = repository.getFavorites()
            favoritesLiveData.postValue(favoriteIds)
        }
    }

    fun toggleFavorite(restaurantId: Long, restaurantName: String, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            repository.toggleFavorite(restaurantId)
            val updatedFavorites = repository.getFavorites()
            favoritesLiveData.postValue(updatedFavorites)

            val message = if (restaurantId in updatedFavorites) {
                "$restaurantName añadido a favoritos"
            } else {
                "$restaurantName eliminado de favoritos"
            }
            onComplete(message)
        }
    }

    fun addRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            try {
                val newRestaurant = addRestaurantUseCase(restaurant)
                if (newRestaurant != null) {
                    val currentList = restaurantLiveData.value?.toMutableList() ?: mutableListOf()
                    currentList.add(newRestaurant)
                    restaurantLiveData.postValue(currentList)
                } else {
                    errorLiveData.postValue("Error al añadir el restaurante")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message ?: "Error desconocido")
            }
        }
    }

    fun deleteRestaurant(id: Long) {
        viewModelScope.launch {
            try {
                deleteRestaurantUseCase(id)
                val updatedRestaurants = getRestaurantsUseCase()
                restaurantLiveData.postValue(updatedRestaurants)
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun editRestaurant(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        viewModelScope.launch {
            try {
                Log.d("RestaurantViewModel", "Editando restaurante de $oldRestaurant a $newRestaurant")
                editRestaurantUseCase(oldRestaurant, newRestaurant)
                getRestaurants()
            } catch (e: Exception) {
                Log.e("RestaurantViewModel", "Error al editar restaurante", e)
                errorLiveData.postValue(e.message ?: "Error desconocido")
            }
        }
    }
}
