package com.example.ejemplomd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.ejemplomd.R
import com.example.ejemplomd.databinding.ActivityMainBinding
import com.example.ejemplomd.databinding.NavHeaderBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // se asocia el XML del layout de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navView = binding.navView
        // se asocia el layout de la cabecera del menu
        val headerBinding = NavHeaderBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // se asocia el toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size)
        }

        // establece el color del texto en el Toolbar
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        /* se asocia el header al nav */
        navView.addHeaderView(headerBinding.root)

        // se establece texto e imagen del header del menu
        headerBinding.headerTitle.text = "¡Usuario!"
        headerBinding.imageView.setImageResource(R.drawable.ic_user)

        // se asocia el archivo drawer_menu al navView
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        // Cambiar el color del ícono de hamburguesa y flecha
        drawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        /* Configuran el identificar la selección de cada opción del menu */
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, HomeFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.nav_home)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> null
            }
            // actualizar el contenido del container de fragmentos
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, it)
                    .commit()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            } ?: false
        }
    }


    fun onBackPressedDispatcher() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}