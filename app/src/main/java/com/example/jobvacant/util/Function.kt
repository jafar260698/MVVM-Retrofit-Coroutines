package com.example.jobvacant.util

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
class Function{
    companion object{

        fun hasInternetConnection(application: Application):Boolean{
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
}

