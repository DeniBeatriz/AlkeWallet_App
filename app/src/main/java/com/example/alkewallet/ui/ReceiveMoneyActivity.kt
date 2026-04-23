package com.example.alkewallet.ui

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewallet.controller.SessionManager
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain7Binding
import com.example.alkewallet.model.TransactionsModel
import com.example.alkewallet.viewmodel.WalletViewModel
import java.util.Date
import java.util.Locale

class ReceiveMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain7Binding

    private lateinit var sessionManager: SessionManager


    private val viewModel: WalletViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = TransactionsDataBase.getDatabase(applicationContext)
                val repository =
                    WalletRepository(database.transactionsDao(), RetrofitClient.instance)
                return WalletViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.iconBack.setOnClickListener {
            finish()
        }


        binding.txtReceiverValue.setText(sessionManager.getUserName())
        binding.txtReceiverValue.isEnabled = false

        viewModel.operationStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Ingreso realizado con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        binding.btnReceiveContact.setOnClickListener {
            val sender = binding.etSender.text.toString().trim()
            val receiver = sessionManager.getUserName()
            val amountText = binding.etNumberDecimalReceive.text.toString().trim()
            val note = binding.etNoteReceive.text.toString().trim()
            val amount = amountText.toDoubleOrNull()

            when {
                receiver.isEmpty() ->
                    Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()

                amount == null || amount <= 0 ->
                    binding.etNumberDecimalReceive.error = "Ingrese un monto válido mayor a cero"

                else -> {
                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val currentDate = sdf.format(Date())

                    val transaction = TransactionsModel(
                        sender = sender,
                        receiver = receiver,
                        amount = amount,
                        date = currentDate,
                        description = note
                    )

                    viewModel.sendMoney(transaction)
                }
            }
        }
    }
}