package com.example.alkewallet.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkewallet.controller.MainActivity7
import com.example.alkewallet.controller.MainActivity8
import com.example.alkewallet.R
import com.example.alkewallet.model.SendModel

public class MainActivity5 : AppCompatActivity() {

    //Declarar el Modelo y Componente de la vista
    private lateinit var sendModel: SendModel


    private lateinit var txtBalance: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Singleton:misma instancia que MainActivity8
        sendModel = SendModel


        //Vincular los componentes de la vista con las variables
        txtBalance = findViewById(R.id.txtBalance)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnReceive = findViewById<Button>(R.id.btnReceive)
        val imgProfile = findViewById<ImageView>(R.id.imgProfile)
        val imgNotification = findViewById<ImageView>(R.id.imageView7)

        btnSend.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)

        }
        btnReceive.setOnClickListener {
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)

        }
    }
    override fun onResume() {
        super.onResume()
        txtBalance.text = "$${String.format("%.2f", sendModel.getSaldoTotal())}"











    }
}