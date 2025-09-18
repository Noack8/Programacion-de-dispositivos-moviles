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

            // Mostramos en log los usuarios con contraseña en SHA-256
            /*users.forEach {
                //val hashedPass = sha256(it.passwd)
                Log.d("UserInfo", "Name: ${it.name}" +
                        ", Email: ${it.email}, Pass(SHA-256): ${it.passwd}")
                //Log.d("UserInfo", "Name: ${it.name}, Email: ${it.email}, Pass: ${it.passwd}")
            }*/

            listU = users
        }

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
        /*
                // array estático de ejemplo con objetos UserModel
                private val users: Array<UserModel> = arrayOf(
                    UserModel( name = "Juan",
                        email = "aljuan1500@alumno.ipn.mx",
                        passwd = "1240fcf46967c0b28950a8d3e769e69b92e4fd0dabbfee7ab50ce6beca813c31"),
                    UserModel( name = "Laura",
                        email = "allaura1500@alumno.ipn.mx",
                        passwd = "0fdd4ecd5592937dff86c9e980218755600db563b4f265879c8ead528e2a3859"),
                    UserModel( name = "Victoria",
                        email = "alvictoria1500@alumno.ipn.mx",
                        passwd = "ba9805141ec964c798597ebd03fb44f2fc42ebb06074d9700836dfa76f95659f"),
                    UserModel( name = "Oscar",
                        email = "aloscar1400@alumno.ipn.mx",
                        passwd = "03274afc8ab0ad043e215a03a97e160a8fc7675e36eb0b1081f53ff6127be876"),
                    UserModel( name = "Victoria",
                        email = "alelias1400@alumno.ipn.mx",
                        passwd = "79fbbec52216eac7a0d021d0c7d5b4bb223951616877d6bb2b9a2dcaab3b21ee"),
                )*/
    }
}