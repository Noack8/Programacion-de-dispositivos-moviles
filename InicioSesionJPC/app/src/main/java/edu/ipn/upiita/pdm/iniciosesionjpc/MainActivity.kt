package edu.ipn.upiita.pdm.iniciosesionjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import edu.ipn.upiita.pdm.iniciosesionjpc.ui.LoginScreen
import edu.ipn.upiita.pdm.iniciosesionjpc.ui.theme.InicioSesionJPCTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InicioSesionJPCTheme {
                LoginScreen()
              }
            }
        }
    }
