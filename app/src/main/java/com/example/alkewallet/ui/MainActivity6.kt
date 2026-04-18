package com.example.alkewallet.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.controller.TransactionAdapter
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain6Binding
import com.example.alkewallet.viewmodel.WalletViewModel
import com.example.alkewallet.viewmodel.WalletViewModelFactory
import kotlinx.coroutines.launch

class MainActivity6 : AppCompatActivity() {

    private lateinit var binding: ActivityMain6Binding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var viewModel: WalletViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Binding
        binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuar RecyclerView
        setupRecyclerView()
        //Inicializar Adapter con la lisa vacía
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.adapter = transactionAdapter

        //Configurar Factory y ViewModel
        val database = TransactionsDataBase.getDatabase(this)
        val repository = WalletRepository(database.transactionsDao(), RetrofitClient.instance)
        val factory = WalletViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WalletViewModel::class.java)

        //Botones de navegacion
        binding.btnSend.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

        observeViewModel()
        //Cargar datos al iniciar
        loadData()

        viewModel.syncTransactions(123)

    }
    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.adapter = transactionAdapter
    }

    private fun loadData() {
        lifecycleScope.launch {
            val history = viewModel.getAllLocalTransactions()

            //Actualizar lista
            transactionAdapter.updateList(history)

            //calcular y actualizar el saldo dinamicamente
            val total = history.sumOf { it.amount }
            binding.txtBalance.text = "$${String.format("%.2f", total)}"
        }

    }


    private fun observeViewModel() {
        // Observar la lista de transacciones desde la base de datos local
        // Nota: Asegúrate de que tu ViewModel exponga un LiveData con la lista de Room

        
        // Para este ejemplo, simulamos que el ViewModel carga los datos en la base de datos
        // Y luego los obtenemos. Lo ideal es que el ViewModel tenga un LiveData:
        // viewModel.transactionsList.observe(this) { list -> ... }
    }
}
