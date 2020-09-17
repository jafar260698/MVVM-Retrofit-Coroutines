package com.example.restaurants.ui.Api

import android.os.Environment
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ApiClient {
    private val logging = HttpLoggingInterceptor()
        .setLevel(
            HttpLoggingInterceptor
                .Level.BASIC
        )
    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .cache(
            Cache(
                File(Environment.getExternalStorageState() + "/okhttp_cache/"),
                10 * 1024 * 1024
            )
        )
        .build()
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://jafartemirov.uz/food/food_order/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}