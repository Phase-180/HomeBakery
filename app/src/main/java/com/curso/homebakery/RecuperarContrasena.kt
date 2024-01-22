package com.curso.homebakery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.curso.homebakery.databinding.ActivityRecuperarContrasenaBinding
import com.google.firebase.auth.FirebaseAuth

class RecuperarContrasena : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarContrasenaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecuperar.setOnClickListener {
            val email = binding.etEmailRecuperar.text.toString()
            if (email.isNotEmpty()) {
                resetPassword(email)
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al enviar correo de recuperación", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
