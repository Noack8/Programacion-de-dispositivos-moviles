package com.example.practica1.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practica1.model.UserModel
import com.example.practica1.model.UserProvider



class UserViewModel : ViewModel() {
    val userModel = MutableLiveData<UserModel>()

    fun randomUser(context: Context){
        val currentUser = UserProvider.random(context)
        userModel.postValue(currentUser)
    }
}