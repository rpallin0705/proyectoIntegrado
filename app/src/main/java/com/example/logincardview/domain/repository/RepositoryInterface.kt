package com.example.logincardview.domain.repository

interface RepositoryInterface<T> {
    suspend fun getAll() : List<T>
}