package com.example.restaurants.ui.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.edmodo.util.Resource
import com.example.jobvacant.model.Countries
import com.example.jobvacant.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ViewModel(val app:Application, val repositoy:Repository):AndroidViewModel(app) {

    val country :MutableLiveData<Resource<List<Countries>>> = MutableLiveData()


    init {}

    fun getCountry()=viewModelScope.launch { safeCountry() }

    fun searchCountry(key: String)=viewModelScope.launch { safesearchCountry(key)}


    private suspend fun safeCountry(){
        country.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(app)) {
                val response = repositoy.getCountry()
                country.postValue(handleCountry(response))
            }else {
                country.postValue(Resource.Error("No internet connection"))
                Log.d(ContentValues.TAG,"Loading: No Internet")
            }
        }catch (t:Throwable){
            when(t) {
                is IOException -> country.postValue(Resource.Error("Network Failure $t"))
                else -> country.postValue(Resource.Error("Conversion error $t"))
            }
        }
    }

    private suspend fun safesearchCountry(key:String){
        country.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(app)) {
                val response = repositoy.searchCountry(key)
                country.postValue(handleCountry(response))
            }else {
                country.postValue(Resource.Error("No internet connection"))
                Log.d(ContentValues.TAG,"Loading: No Internet")
            }
        }catch (t:Throwable){
            when(t) {
                is IOException -> country.postValue(Resource.Error("Network Failure $t"))
                else -> country.postValue(Resource.Error("Conversion error $t"))
            }
        }
    }

    private fun handleCountry(response: Response<List<Countries>>) :Resource<List<Countries>>{
        if (response.isSuccessful){
            response.body()?.let { resultCode->
                return Resource.Success(resultCode)
            }
        }
        return Resource.Error(response.message())
    }


    private fun hasInternetConnection(application: Application):Boolean{
        val connectivityManager=application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            Log.d(ContentValues.TAG,"SDK_M")
            val activeNetwork=connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else ->false
            }
        }else {
            Log.d(ContentValues.TAG,"SDK_N")
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}