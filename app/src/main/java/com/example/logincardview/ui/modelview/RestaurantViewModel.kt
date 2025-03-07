package com.example.logincardview.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.data.repository.RestaurantInMemoryRepository
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.usecase.restaurant.DeleteRestaurantUseCase
import com.example.logincardview.domain.usecase.restaurant.EditRestaurantUseCase
import com.example.logincardview.domain.usecase.restaurant.GetRestaurantsUseCase
import com.example.logincardview.domain.usecase.restaurant.AddRestaurantUseCase
import kotlinx.coroutines.launch

class RestaurantViewModel() : ViewModel() {

    val restaurantLiveData = MutableLiveData<List<Restaurant>>()
    private val progressBarLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<String>()
    private val repository: RestaurantInMemoryRepository = RestaurantInMemoryRepository()
    private val getRestaurantsUseCase: GetRestaurantsUseCase = GetRestaurantsUseCase(repository)
    private val addRestaurantUseCase: AddRestaurantUseCase = AddRestaurantUseCase(repository)
    private val deleteRestaurantUseCase: DeleteRestaurantUseCase = DeleteRestaurantUseCase(repository)
    private val editRestaurantUseCase: EditRestaurantUseCase = EditRestaurantUseCase(repository)

    fun getRestaurants() {
        progressBarLiveData.value = true
        viewModelScope.launch {
            try {
                val data = getRestaurantsUseCase()
                data.let {
                    restaurantLiveData.value = it
                }
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
            } finally {
                progressBarLiveData.value = false
            }
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


    fun deleteRestaurant(position: Int) {
        viewModelScope.launch {
            try {
                deleteRestaurantUseCase(position)
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