package com.example.practica1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1.R
import com.example.practica1.databinding.ActivityWelcomeBinding
import com.example.practica1.model.UserProvider
import com.example.practica1.viewmodel.UserViewModel
import kotlin.getValue

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtNameUser.setText(UserProvider.getUserSelect())

        binding.btnCamPasswd.setOnClickListener {
            val intent = Intent(this, CambiarPassActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnExit.setOnClickListener { finish() }
    }
}