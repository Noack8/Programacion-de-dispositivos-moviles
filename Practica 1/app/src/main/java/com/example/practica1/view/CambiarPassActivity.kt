package com.example.practica1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1.R
import com.example.practica1.databinding.ActivityCambiarPassBinding
import com.example.practica1.databinding.ActivityPreguntaBinding
import com.example.practica1.viewmodel.UserViewModel
import com.example.practica1.model.UserProvider
import kotlinx.coroutines.delay
import kotlin.getValue

class CambiarPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCambiarPassBinding

    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            val pw:String = binding.editTxtPwCambio.text.toString()
            binding.editTxtPwCambio.text.clear()
            if(UserProvider.setUserNewPw(this,pw)){
                binding.txtTituloCPA.text = "Password cambiada"
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                binding.txtTituloCPA.text = "Error de Password"

            }
        }
    }
}