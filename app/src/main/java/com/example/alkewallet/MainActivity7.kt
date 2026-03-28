package com.example.alkewallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.model.SendModel

class MainActivity7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main7)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iconBack = findViewById<ImageView>(R.id.iconBack)
        val btnReceiveContact = findViewById<Button>(R.id.btnReceiveContact)
        val editTextMonto = findViewById<EditText>(R.id.etNumberDecimalReceive)

        iconBack.setOnClickListener {
            finish()
        }

        btnReceiveContact.setOnClickListener {
            val monto = editTextMonto.text.toString()
            if (monto.isEmpty()) {
                editTextMonto.error = "El monto es requerido"
                return@setOnClickListener
            } else {
                val montoDouble = monto.toDoubleOrNull()
                if (montoDouble == null || montoDouble <= 0) {
                    editTextMonto.error = "El monto debe ser mayor a cero"
                } else {

                    val exito = SendModel.sumarSaldo(montoDouble)

                    if (exito) {
                        Toast.makeText(this, "Transferencia de $$montoDouble realizada", Toast.LENGTH_SHORT).show()
                        
                        // Volver a la pantalla 5 enviando el saldo actualizado
                        val intent = Intent(this, MainActivity5::class.java)
                        intent.putExtra("saldoTotal", SendModel.getSaldoTotal())
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al procesar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    } // Llave que cierra onCreate
} // Llave que cierra la Clase
