package com.example.restaurants.ui.Api

import com.example.jobvacant.model.Countries
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("getCountries.php")
    suspend fun getCountries():Response<List<Countries>>

    @GET("searchCountries.php")
    suspend fun searchCountries(@Query("key") search:String):Response<List<Countries>>
}