package com.example.alkewallet.ui

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewallet.controller.SelectContactActivity
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.model.TransactionsModel
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain8Binding
import com.example.alkewallet.model.SendModel
import com.example.alkewallet.viewmodel.WalletViewModel
import java.util.Locale

class MainActivity8 : AppCompatActivity() {

    private lateinit var binding: ActivityMain8Binding
    private val sendModel = SendModel // Acceso al Singleton

    private val viewModel: WalletViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = TransactionsDataBase.getDatabase(applicationContext)
                val repository = WalletRepository(database.transactionsDao(), RetrofitClient.instance)
                return WalletViewModel(repository) as T
            }
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedContact = result.data?.getStringExtra("selected_contact")
            binding.etReceiver.setText(selectedContact)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBack.setOnClickListener { finish() }

        binding.btnPickContact.setOnClickListener {
            val intent = Intent(this, SelectContactActivity::class.java)
            startForResult.launch(intent)
        }

        binding.btnSendContact.setOnClickListener {
            val sender = binding.etSender.text.toString().trim()
            val receiver = binding.etReceiver.text.toString().trim()
            val amount = binding.etNumberDecimalSend.text.toString().toDoubleOrNull() ?: 0.0
            val note = binding.etNoteSend.text.toString().trim()

            if (sender.isEmpty() || receiver.isEmpty() || amount <= 0) {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar saldo y realizar operación
            if (sendModel.restarSaldo(amount)) {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val currentDate = sdf.format(System.currentTimeMillis())

                val transaction = TransactionsModel(
                    sender = sender,
                    receiver = receiver,
                    amount = amount * -1,
                    date = currentDate,
                    description = note
                )

                viewModel.sendMoney(transaction) { success ->
                    if (success) {
                        Toast.makeText(this, "Transferencia realizada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al enviar la transacción", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
