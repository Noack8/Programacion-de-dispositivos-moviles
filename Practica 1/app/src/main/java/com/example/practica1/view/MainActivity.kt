package com.example.practica1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica1.databinding.ActivityMainBinding
import com.example.practica1.model.UserProvider
import com.example.practica1.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserProvider.readUsersFromAssets(this)

        userViewModel.userModel.observe(this, {userModel ->
            binding.textName.text = userModel.name
            binding.TextEmail.text = userModel.email
        })

        //binding.viewContainer.setOnClickListener { userViewModel.randomUser(context = this)  }

        /* Logica de negocio del boton iniciar sesion */
        binding.btnIniciarSesion.setOnClickListener {
            val email:String = binding.txtEditEmail.text.toString()
            val pw:String = binding.txtEditPsswd.text.toString()
            //Log.d("UserInfo","Tu: ${email} && ${pw}")
            if (UserProvider.validateEmailPw(email,pw)){
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
            }
            else{
                finish()
            }
        }
    }
}