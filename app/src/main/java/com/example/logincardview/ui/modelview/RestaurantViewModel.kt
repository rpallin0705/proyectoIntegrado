package com.example.logincardview.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.data.repository.RestaurantInMemoryRepository
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.usecase.GetRestaurantsUseCase
import com.example.logincardview.domain.usecases.AddRestaurantUseCase
import kotlinx.coroutines.launch

class RestaurantViewModel() : ViewModel() {

    val restaurantLiveData = MutableLiveData<List<Restaurant>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val repository: RestaurantInMemoryRepository = RestaurantInMemoryRepository()
    val getRestaurantsUseCase: GetRestaurantsUseCase = GetRestaurantsUseCase(repository)
    private val addRestaurantUseCase: AddRestaurantUseCase = AddRestaurantUseCase(repository)

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

                val updatedRestaurants = getRestaurantsUseCase()
                restaurantLiveData.postValue(updatedRestaurants)
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
            }
        }
    }


}
