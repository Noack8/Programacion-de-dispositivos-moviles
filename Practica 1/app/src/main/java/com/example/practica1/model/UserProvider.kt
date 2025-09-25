package com.example.practica1.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import java.security.MessageDigest

class UserProvider {
    companion object {
        private lateinit var listU:List<UserModel>
        private lateinit var user: UserModel

        fun getUser(): UserModel{ return user }
        fun getUserSelect(): String{ return user.name }
        fun getUserPregunta(): String{ return user.pregunta }
        fun random(context: Context):UserModel{
            val num = (0..4).random()
            readUsersFromAssets(context)
            return listU[num]
            //return users[num]
        }

        //Funcion para cambiar la contrasena version del profesor
        fun setUserNewPwV2(context: Context,pw: String,pw2: String): Boolean {
            if(pw.isNotEmpty() && pw.length >= 8 && pw.contains(Regex("[A-Z]"))
                && pw.contains(Regex("[0-9]"))
                && pw.contains(Regex("[_.#$?]"))
                && pw == pw2) {
                val pwSha256: String = sha256(pw)
                val gson = Gson()
                listU.forEach {
                    if (it.email == user.email) {
                        it.passwd = pwSha256
                    }
                }
                // Convertir el objeto a JSON
                val updatedJson = gson.toJson(listU)
                // Obtener la ruta del archivo en el almacenamiento interno (puedes usar external storage si prefieres)
                val file = File(context.filesDir, "user.json")

                // Escribir el JSON actualizado en el archivo
                FileWriter(file).use { writer ->
                    writer.write(updatedJson)
                }
                return true
            }else{
                return false
            }
        }

        //Funcion para cambiar la contraseña
        fun setUserNewPw(context: Context,pw: String,pw2: String): Boolean{
            if(pw.isNotEmpty() && pw.length >= 8 && pw.contains(Regex("[A-Z]"))
            && pw.contains(Regex("[0-9]"))
            && pw.contains(Regex("[_.#$?]"))
            && pw == pw2){
                val pwSha256: String = sha256(pw)
                val json: String = context.assets.open("users.json")
                    .bufferedReader()
                    .use { it.readText() }
                val gson = Gson()
                val userType = object : TypeToken<List<UserModel>>() {}.type
                val users: List<UserModel> = gson.fromJson(json, userType)
                users.forEach {
                    if(it.email == user.email){
                        it.passwd = pwSha256
                    }
                }
                val jsonString = gson.toJson(users)
                Log.d("Json", jsonString)
                context.openFileOutput("users.json", Context.MODE_PRIVATE).use {
                    it.write(jsonString.toByteArray())
                }
                users.forEach {
                    Log.d("UserInfo", "Name: ${it.name}" +
                            ", Email: ${it.email}, Pass(SHA-256): ${it.passwd}")
                }
                listU = users
                return true
            }

            return false
        }

        // función para leer usuarios del JSON y parsearlos a data class
        fun readUsersFromAssets(context: Context){
            val json: String = context.assets.open("users.json")
                .bufferedReader()
                .use { it.readText() }
            val gson = Gson()
            val userType = object : TypeToken<List<UserModel>>() {}.type
            val users: List<UserModel> = gson.fromJson(json, userType)
            users.forEach {
                Log.d("UserInfo", "Name: ${it.name}" +
                        ", Email: ${it.email}, Pass(SHA-256): ${it.passwd}")
            }
            listU = users
        }

        //Funcion que valida que el correo esta en el json
        fun validateEmailforPregunta (email:String): Boolean{
            listU.forEach {
                Log.d("UserInfo","Email: ${it.email} == Tu: ${email}")
                if(email.isNotEmpty() && email == it.email) {
                    user = it
                    return true
                }
            }
            return false
        }
        //Funcion que valida que la respuesta sea la correcta
        fun validateRespuesta (respuesta:String): Boolean{
            val resSha256 : String = sha256(respuesta.lowercase())
            if(respuesta.isNotEmpty() && resSha256 == user.respuesta) {
                return true
            }
            return false
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