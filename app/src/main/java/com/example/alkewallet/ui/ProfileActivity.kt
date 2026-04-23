package com.example.alkewallet.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewallet.R
import com.example.alkewallet.controller.SessionManager
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.data.network.UserEntity
import com.example.alkewallet.databinding.ActivityMain9Binding
import com.example.alkewallet.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain9Binding
    private lateinit var sessionManager: SessionManager

    private val viewModel: ProfileViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = TransactionsDataBase.getDatabase(applicationContext)
                return ProfileViewModel(database.userDao()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeUser()

        val sessionName = sessionManager.getUserName()
        val sessionEmail = sessionManager.getUserEmail()
        val sessionImage = sessionManager.getProfileImageUrl()

        if (sessionName.isNotEmpty() && sessionEmail.isNotEmpty()) {
            val user = UserEntity(
                id = 1,
                name = sessionName,
                email = sessionEmail,
                imageUrl = sessionImage
            )
            viewModel.saveUser(user)
        }

        viewModel.loadUser(1)
    }

    private fun observeUser() {
        viewModel.user.observe(this) { user ->
            binding.txtProfileName.text = user.name

            if (user.imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(user.imageUrl)
                    .placeholder(R.drawable.icon_profile)
                    .error(R.drawable.icon_profile)
                    .into(binding.imgProfile2)
            } else {
                binding.imgProfile2.setImageResource(R.drawable.icon_profile)
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}