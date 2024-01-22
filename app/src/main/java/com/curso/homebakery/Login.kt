package com.curso.homebakery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.curso.homebakery.databinding.ActivityLoginBinding

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val auth = AuthManager(this)//(application as App).auth
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnLoginGoogle: Button = findViewById(R.id.btnLoginGoogle)
        val etEmail: TextView = findViewById(R.id.etEmail)
        val etContrasena: TextView = findViewById(R.id.etContrasena)


        val googleSignLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            when (val account =
                auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
                is AuthRes.Success -> {
                    val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
                    GlobalScope.launch {
                        val firebaseUser = auth.googleSignInCredential(credential)
                        when (firebaseUser) {
                            is AuthRes.Success -> {
                                Snackbar.make(
                                    binding.root,
                                    "Inicio de sesión correcto",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            }

                            is AuthRes.Error -> {
                                Snackbar.make(
                                    binding.root,
                                    "Error al iniciar sesión",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                is AuthRes.Error -> {
                    Snackbar.make(binding.root, "Error al iniciar sesión", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }


        if (auth.getCurrentUser() != null) {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }

        with(binding) {
            btnRegistrar.setOnClickListener {
                val intent = Intent(this@Login, CrearCuenta::class.java)
                startActivity(intent)
            }

            btnLogin.setOnClickListener {
                emailPassSignIn(etEmail.text.toString(), etContrasena.text.toString())
            }

            btnRestaurarContrasena.setOnClickListener {
                val intent = Intent(this@Login, RecuperarContrasena::class.java)
                startActivity(intent)
            }

            btnLoginGoogle.setOnClickListener {
                auth.signInWithGoogle(googleSignLauncher)
            }
        }

    }



    private fun emailPassSignIn(eMail: String, password: String) {
        if (!eMail.isNullOrEmpty() && !password.isNullOrEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                when (auth.signInWithEmailAndPassword(
                    eMail,
                    password
                )) {
                    is AuthRes.Success -> {
                        Snackbar.make(
                            binding.root,
                            "Inicio de sesión correcto",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    }

                    is AuthRes.Error -> {
                        Snackbar.make(
                            binding.root,
                            "Error al iniciar sesión",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}