package com.example.alkewallet.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.R
import com.example.alkewallet.controller.SessionManager
import com.example.alkewallet.databinding.ActivityMain3Binding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            validateFields()
        }

        binding.txtNewAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.txtForgotAccount.setOnClickListener {
            Toast.makeText(this, R.string.alert_forgot_password, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        when {
            email.isEmpty() -> binding.etEmail.error = "Ingresa tu correo"

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                binding.etEmail.error = "Ingresa un correo válido"

            password.isEmpty() -> binding.etPassword.error = "Ingresa tu contraseña"

            else -> {
                val userName = email.substringBefore("@")

                sessionManager.saveUserSession(
                    name = userName,
                    email = email
                )

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
