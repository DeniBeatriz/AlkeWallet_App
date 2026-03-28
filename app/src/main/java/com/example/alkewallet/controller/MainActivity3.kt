package com.example.alkewallet.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.controller.MainActivity4
import com.example.alkewallet.controller.MainActivity5
import com.example.alkewallet.R

class MainActivity3 : AppCompatActivity() {

    private lateinit var editTextTextEmailAddress: EditText
    private lateinit var editTextTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtNewAccount: TextView
    private lateinit var txtForgotAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtNewAccount = findViewById(R.id.txtNewAccount)
        txtForgotAccount = findViewById(R.id.txtForgotAccount)

        btnLogin.setOnClickListener {
            validarYRedirigir()
        }

        txtNewAccount.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }

        txtForgotAccount.setOnClickListener {
            Toast.makeText(this, R.string.alert_forgot_password, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarYRedirigir() {
        // Obtener los valores ingresados por el usuario
        val email = editTextTextEmailAddress.text.toString().trim()
        val password = editTextTextPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            // Mostrar un mensaje de error si los campos están vacíos
            Toast.makeText(this, R.string.alert_empty_fields, Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
            finish()
        }
    }
}