package com.curso.homebakery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.curso.homebakery.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val auth = AuthManager(this)//(application as App).auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_graph -> {
                    navController.navigate(R.id.mainFragment)
                    true
                }
                R.id.segundoFragment -> {
                    navController.navigate(R.id.segundoFragment)
                    true
                }
                R.id.tercerFragment -> {
                    navController.navigate(R.id.tercerFragment)
                    true
                }
                R.id.signOut -> {
                    auth.signOut()
                    signOut()
                    true
                }
                else -> false
            }
        }
    }

    private fun signOut() {
        // Lógica para cerrar sesión
        FirebaseAuth.getInstance().signOut()

        // Redirige al usuario a la pantalla de Login
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}
