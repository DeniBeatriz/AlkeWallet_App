package com.example.alkewallet.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        val adapter = ContactAdapter { name ->
            val intent = Intent()
            intent.putExtra("selected_contact", name)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.rvContacts.adapter = adapter

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getUsers()

                if (response.isSuccessful) {
                    val users = response.body().orEmpty()
                    adapter.setContacts(users)
                } else {
                    Toast.makeText(
                        this@SelectContactActivity,
                        "No fue posible cargar los contactos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@SelectContactActivity,
                    "Error de conexión al cargar contactos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}