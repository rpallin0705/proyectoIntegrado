package com.example.logincardview.ui.modelview

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


    fun addRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            try {
                addRestaurantUseCase(restaurant)
                val currentList = restaurantLiveData.value?.toMutableList() ?: mutableListOf()
                currentList.add(restaurant)
                restaurantLiveData.postValue(currentList)
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
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
                editRestaurantUseCase(oldRestaurant, newRestaurant)

                val currentList = restaurantLiveData.value?.toMutableList() ?: mutableListOf()
                val index = currentList.indexOfFirst { it.name == oldRestaurant.name }
                if (index != -1) {
                    currentList[index] = newRestaurant
                    restaurantLiveData.postValue(currentList)
                }
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
            }
        }
    }
}
