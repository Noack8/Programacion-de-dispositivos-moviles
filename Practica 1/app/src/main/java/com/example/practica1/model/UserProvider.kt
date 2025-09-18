package com.example.practica1.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.security.MessageDigest

class UserProvider {
    companion object {
        private lateinit var listU:List<UserModel>
        private lateinit var user: UserModel

        fun getUserSelect(): String{ return user.name }
        fun random(context: Context):UserModel{
            val num = (0..4).random()
            readUsersFromAssets(context)
            return listU[num]
            //return users[num]
        }

        fun setUserNewPassword(context: Context,pw: String){
            val json: String = context.assets.open("users.json")
                .bufferedReader()
                .use { it.readText() }
        }

        // función para leer usuarios del JSON y parsearlos a data class
        fun readUsersFromAssets(context: Context){
            val json: String = context.assets.open("users.json")
                .bufferedReader()
                .use { it.readText() }
            val gson = Gson()
            val userType = object : TypeToken<List<UserModel>>() {}.type
            val users: List<UserModel> = gson.fromJson(json, userType)
            listU = users
        }

        //Funcion que valida que los datos que dio el usuario estan en el json
        fun validateEmailPw (email:String, pw: String): Boolean{
            val pwSha256: String = sha256(pw)
            listU.forEach {
                Log.d("UserInfo","Email: ${it.email} == Tu: ${email}" +
                        "Pw: ${it.passwd} == Tu: ${pwSha256}")
                if(email.isNotEmpty() && pw.isNotEmpty() && email == it.email &&
                    pwSha256 == it.passwd) {
                    user = it
                    return true
                }
            }
            return false
        }

        // Función para generar hash SHA-256
        fun sha256(input: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
            return hashBytes.joinToString("") { "%02x".format(it) }.uppercase()
        }
    }
}