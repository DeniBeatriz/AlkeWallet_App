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

class MainActivity8 : AppCompatActivity() {



    private lateinit var sendModel: SendModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main8)

        // Configuración de Insets para el diseño EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Instanciar el modelo
        sendModel = SendModel


        //Vincular los componentes XML con las variables
        val btnSendContact = findViewById<Button>(R.id.btnSendContact)
        val iconBack = findViewById<ImageView>(R.id.iconBack)
        val editTextNumberDecimal = findViewById<EditText>(R.id.etNumberDecimalSend)

        // Botón Volver
        iconBack.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior
        }

        // Botón Enviar/Recibir
        btnSendContact.setOnClickListener {
            val monto = editTextNumberDecimal.text.toString()

            if (monto.isEmpty()) {
                editTextNumberDecimal.error = "El monto es requerido"
                return@setOnClickListener
            } else {
                val montoDouble = monto.toDoubleOrNull()
                
                if (montoDouble == null || montoDouble <= 0) {
                    editTextNumberDecimal.error = "El monto debe ser mayor a cero"
                } else {
                    val exito = sendModel.restarSaldo(montoDouble)

                    if (exito) {
                        Toast.makeText(this, "Transferencia de $$montoDouble realizada", Toast.LENGTH_SHORT).show()
                        finish() // Finaliza para que no se pueda volver atrás al formulario
                    } else {
                        Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()


                    }


                    

                }
            }
        }
    }
}
