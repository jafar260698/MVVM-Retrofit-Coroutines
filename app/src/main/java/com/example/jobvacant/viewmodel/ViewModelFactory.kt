package com.example.restaurants.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobvacant.repository.Repository

class ViewModelFactory(val app:Application, val repository: Repository)
    :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return ViewModel(app,repository) as T
    }
}