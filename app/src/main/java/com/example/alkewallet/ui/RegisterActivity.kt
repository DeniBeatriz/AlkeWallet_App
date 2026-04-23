package com.example.alkewallet.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.databinding.ActivityMain4Binding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCreateAccount2.setOnClickListener {
            validateFields()
        }

        binding.txtGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateFields() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail2.text.toString().trim()
        val password = binding.etCreatePassword.text.toString().trim()
        val confirmPassword = binding.etConfirmedPassword.text.toString().trim()

        when {
            name.isEmpty() -> binding.etName.error = "Ingresa tu nombre"

            email.isEmpty() -> binding.etEmail2.error = "Ingresa tu correo"

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                binding.etEmail2.error = "Ingresa un correo válido"

            password.isEmpty() ->
                binding.etCreatePassword.error = "Ingresa tu contraseña"

            password.length < 6 ->
                binding.etCreatePassword.error = "La contraseña debe tener al menos 6 caracteres"

            confirmPassword.isEmpty() ->
                binding.etConfirmedPassword.error = "Confirma tu contraseña"

            password != confirmPassword ->
                binding.etConfirmedPassword.error = "Las contraseñas no coinciden"

            else -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
