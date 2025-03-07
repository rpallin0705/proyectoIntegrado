package com.example.logincardview.domain.repository

import com.example.logincardview.domain.models.Restaurant

interface RepositoryInterface<T> {
    suspend fun getAll() : List<T>
    suspend fun delete(id : Long): Boolean
    suspend fun add(o: T)
    suspend fun edit(oldRestaurant: T, newRestaurant: T)

}