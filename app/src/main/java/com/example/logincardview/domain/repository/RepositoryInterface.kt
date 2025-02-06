package com.example.logincardview.domain.repository

import com.example.logincardview.domain.models.Restaurant

interface RepositoryInterface<T> {
    suspend fun getAll() : List<T>
    suspend fun edit() : Restaurant
    suspend fun delete(): Boolean
    abstract suspend fun add(restaurant: T)

}