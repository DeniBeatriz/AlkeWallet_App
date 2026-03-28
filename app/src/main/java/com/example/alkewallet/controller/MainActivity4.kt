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
import com.example.alkewallet.controller.MainActivity5
import com.example.alkewallet.R

class MainActivity4 : AppCompatActivity() {

    private lateinit var editTextTextName: EditText
    private lateinit var editTextTextEmailAddress2: EditText
    private lateinit var editTextTextPassword2: EditText
    private lateinit var editTextTextPassword3: EditText
    private lateinit var btnCreateAccount2: Button
    private lateinit var txtAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de componentes
        editTextTextName = findViewById(R.id.editTextTextName)
        editTextTextEmailAddress2 = findViewById(R.id.editTextTextEmailAddress2)
        editTextTextPassword2 = findViewById(R.id.editTextPassword1)
        editTextTextPassword3 = findViewById(R.id.editTextPassword2)
        btnCreateAccount2 = findViewById(R.id.btnCreateAccount2)
        txtAccount = findViewById(R.id.txtAccount)

        btnCreateAccount2.setOnClickListener {
            validarYRedirigir()
        }

        txtAccount.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
        }
    }

    private fun validarYRedirigir() {
        // Obtener los valores ingresados (.text en lugar de .text())
        val name = editTextTextName.text.toString().trim()
        val email = editTextTextEmailAddress2.text.toString().trim()
        val password = editTextTextPassword2.text.toString().trim()
        val confirmPassword = editTextTextPassword3.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, R.string.alert_empty_fields, Toast.LENGTH_SHORT).show()
            return
        } else if (password != confirmPassword) {
            Toast.makeText(this, R.string.alert_coincide_password, Toast.LENGTH_SHORT).show()
            return
        } else {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
            finish()
        }
    }
}