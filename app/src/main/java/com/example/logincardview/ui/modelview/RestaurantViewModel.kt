package com.example.logincardview.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.usecase.GetRestaurantsUseCase
import kotlinx.coroutines.launch

class RestaurantViewModel(private val getRestaurantsUseCase: GetRestaurantsUseCase) : ViewModel() {

    val restaurantLiveData = MutableLiveData<List<Restaurant>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

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
}
