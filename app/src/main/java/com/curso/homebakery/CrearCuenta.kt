package com.curso.homebakery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.curso.homebakery.databinding.ActivityCrearCuentaBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CrearCuenta : AppCompatActivity() {
    private lateinit var auth: AuthManager // Asegúrate de que AuthManager esté correctamente implementado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = AuthManager(this) // Inicialización de AuthManager

        with(binding) {
            btnRegistrar.setOnClickListener {
                signUp(etEmail.text.toString(), etContrasena.text.toString())
            }
            btnLogin.setOnClickListener {
                finish()
            }
        }
    }

    private fun signUp(eMail: String, password: String) {
        if (eMail.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch {
                when (auth.createUserWithEmailAndPassword(eMail, password)) {
                    is AuthRes.Success -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Usuario creado correctamente",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    is AuthRes.Error -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Error al crear el usuario",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Debes llenar todos los campos",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}
