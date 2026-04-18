package com.example.alkewallet.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.databinding.ActivitySelectContactBinding
import kotlinx.coroutines.launch

class SelectContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del adaptador con la acción de selección
        val adapter = ContactAdapter { name ->
            val intent = Intent()
            intent.putExtra("selected_contact", name)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.rvContacts.adapter = adapter

        // Carga de usuarios desde la API
        lifecycleScope.launch {
            try {
                val users = RetrofitClient.instance.getUsers()
                if (users.isNotEmpty()) {
                    adapter.setContacts(users)
                }
            } catch (e: Exception) {
                // Aquí podrías mostrar un Toast en caso de error de red
            }
        }
    }
}