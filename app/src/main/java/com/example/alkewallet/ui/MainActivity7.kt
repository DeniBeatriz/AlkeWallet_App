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
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.model.TransactionsModel
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain7Binding
import com.example.alkewallet.model.SendModel
import com.example.alkewallet.viewmodel.WalletViewModel
import java.util.Date
import java.util.Locale

class MainActivity7 : AppCompatActivity() {

    private lateinit var binding: ActivityMain7Binding

    private val viewModel: WalletViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = TransactionsDataBase.getDatabase(applicationContext)
                val repository = WalletRepository(database.transactionsDao(), RetrofitClient.instance)
                return WalletViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnReceiveContact.setOnClickListener {
            val montoStr = binding.etNumberDecimalReceive.text.toString()
            val monto = montoStr.toDoubleOrNull() ?: 0.0

            if (monto <= 0) {
                binding.etNumberDecimalReceive.error = "Ingrese un monto válido mayor a cero"
            } else {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val currentDate = sdf.format(Date())

                val transaction = TransactionsModel(
                    sender = "Ingreso Externo",
                    receiver = binding.etReceiver.text.toString(),
                    amount = monto,
                    date = currentDate,
                    description = binding.etNoteReceive.text.toString()
                )

                viewModel.sendMoney(transaction) { success ->
                    if (success) {
                        SendModel.sumarSaldo(monto)
                        Toast.makeText(this, "Ingreso realizado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al procesar el ingreso", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
