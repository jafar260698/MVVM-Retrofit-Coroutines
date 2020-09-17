package com.example.jobvacant.repository

import com.example.restaurants.ui.Api.ApiClient
import com.example.restaurants.ui.Api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.math.cos

class Repository {
    private var Api:ApiService=ApiClient.createService(ApiService::class.java)

    suspend fun getCountry()=Api.getCountries()

    suspend fun searchCountry(string: String)=Api.searchCountries(string)

}