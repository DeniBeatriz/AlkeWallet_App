package com.example.alkewallet.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.controller.MainActivity2
import com.example.alkewallet.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Clase Handler para que la pantalla se muestre un determinado tiempo (3 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Se crea el salto a Login/Signup
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            // Se cierra esta pantalla para que no pueda regresar al logo
            finish()
        }, 3000)
    }
}