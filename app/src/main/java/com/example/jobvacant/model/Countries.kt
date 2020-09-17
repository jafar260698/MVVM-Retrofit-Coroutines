package com.example.jobvacant.model

import java.io.Serializable

data class Countries(
    val id:Int,val country:String,val capital:String,val image:String) :Serializable{
}