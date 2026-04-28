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
import com.example.alkewallet.controller.SessionManager
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain8Binding
import com.example.alkewallet.model.TransactionsModel
import com.example.alkewallet.viewmodel.WalletViewModel
import java.util.Locale

class SendMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain8Binding
    private lateinit var sessionManager: SessionManager

    //Esta variable no reemplaza al ViewModel, sólo guardará el último valor observado desde balance
    //luego hacer el observador del saldo en el onCreate
    private var availableBalance: Double = 0.0


    //con esto la vista obtiene el estado desde su propio ViewMdel, que a su vez utiliza Room y Retrofit a través del Repository
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

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedContact = result.data?.getStringExtra("selected_contact")
                binding.etReceiver.setText(selectedContact)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        binding.txtSenderValue.text = sessionManager.getUserName()



        binding.iconBack.setOnClickListener { finish() }

        binding.btnPickContact.setOnClickListener {
            val intent = Intent(this, SelectContactActivity::class.java)
            startForResult.launch(intent)
        }

        viewModel.operationStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Transferencia realizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        //Para observar el saldo del ViewModel
        //La Activity siempre tendrá el saldo actualizado calculado desde Room por el ViewModel
        viewModel.balance.observe(this) { balance ->
            availableBalance = balance
            binding.txtAvailableBalance.text = "Saldo disponible: $${String.format("%.2f", balance)}"
        }

        //Para cargar las transacciones desde Room
        viewModel.loadLocalTransactions()


        binding.btnSendContact.setOnClickListener {
            val sender = sessionManager.getUserName()
            val receiver = binding.etReceiver.text.toString().trim()
            val amountText = binding.etNumberDecimalSend.text.toString().trim()
            val note = binding.etNoteSend.text.toString().trim()
            val amount = amountText.toDoubleOrNull()

            when {
                sender.isEmpty() ->
                    Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()

                receiver.isEmpty() ->
                    binding.etReceiver.error = "Ingrese el nombre del destinatario"

                amount == null || amount <= 0 ->
                    binding.etNumberDecimalSend.error = "Ingrese un monto válido mayor a cero"

                amount > availableBalance ->
                    binding.etNumberDecimalSend.error = "Saldo insuficiente"


                else -> {
                    val currentDate = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())

                    val transaction = TransactionsModel(
                        sender = sender,
                        receiver = receiver,
                        amount = amount,
                        date = currentDate,
                        description = note
                    )

                    viewModel.sendMoney(transaction, "EXPENSE")
                }
            }
        }
    }
}