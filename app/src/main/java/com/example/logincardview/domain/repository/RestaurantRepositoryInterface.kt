package com.example.logincardview.domain.repository

interface RestaurantRepositoryInterface<T> {
    suspend fun getAll() : List<T>
}