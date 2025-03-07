package com.example.logincardview.network

import com.example.logincardview.utils.MySharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.138:8081"
    /*private const val BASE_URL = "http://10.0.2.2:8081"*/

    private val authInterceptor = Interceptor { chain ->
        val token = MySharedPreferences.getToken()
        val request = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(request.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
    val restaurantApi: RestaurantApiService by lazy { retrofit.create(RestaurantApiService::class.java) }
}