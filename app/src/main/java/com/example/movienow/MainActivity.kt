package com.example.movienow

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movienow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama a installSplashScreen() ANTES de super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Conectar el NavController con la BottomNavigationView
        binding.bottomNavigation.setupWithNavController(navController)

        // --- Lógica para mostrar/ocultar la barra de navegación ---
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                // En estas pantallas, la barra SÍ es visible
                R.id.homeFragment,
                R.id.searchFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                // En todas las demás (como la de detalle), se oculta
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }
}