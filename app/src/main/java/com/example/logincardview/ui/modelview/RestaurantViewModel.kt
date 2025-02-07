package com.example.logincardview.ui.modelview

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.data.repository.RestaurantInMemoryRepository
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.usecase.DeleteRestaurantUseCase
import com.example.logincardview.domain.usecase.EditRestaurantUseCase
import com.example.logincardview.domain.usecase.GetRestaurantsUseCase
import com.example.logincardview.domain.usecases.AddRestaurantUseCase
import com.example.logincardview.ui.views.fragment.RestaurantFragment
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

                val updatedRestaurants = getRestaurantsUseCase()
                restaurantLiveData.postValue(updatedRestaurants)
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
                val updatedRestaurants = getRestaurantsUseCase()
                restaurantLiveData.postValue(updatedRestaurants)
            } catch (e: Exception) {
                errorLiveData.value = e.message ?: "Error desconocido"
            }
        }
    }
}