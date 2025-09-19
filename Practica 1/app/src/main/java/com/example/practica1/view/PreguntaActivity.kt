package com.example.practica1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import com.example.practica1.R
import com.example.practica1.databinding.ActivityPreguntaBinding
import com.example.practica1.viewmodel.UserViewModel
import com.example.practica1.model.UserProvider


class PreguntaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreguntaBinding

    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.userModel.observe(this, { userModel ->
            binding.txtPregunta.text = userModel.pregunta
        })

        binding.btnVerificar.setOnClickListener {
            val email:String = binding.editTxtEmail.text.toString()
            binding.editTxtEmail.text.clear()
            if(UserProvider.validateEmailforPregunta(email)){
                binding.txtPregunta.text = UserProvider.getUserPregunta()
            }
            else{
                binding.txtPregunta.text = "Error de correo"
            }
        }

        binding.btnComprobar.setOnClickListener {
            val respuesta:String = binding.editTxtRespuesta.text.toString()
            binding.editTxtRespuesta.text.clear()
            if(UserProvider.validateRespuesta(respuesta)){
                binding.txtPregunta.text = "Correcto"
                val intent = Intent(this, CambiarPassActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                binding.txtPregunta.text = "Error de respuesta"
            }
        }
    }
}