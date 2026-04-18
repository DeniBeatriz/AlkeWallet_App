package com.example.alkewallet.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.alkewallet.controller.TransactionAdapter
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain5Binding
import com.example.alkewallet.viewmodel.WalletViewModel
import com.example.alkewallet.viewmodel.WalletViewModelFactory
import kotlinx.coroutines.launch
import com.example.alkewallet.data.api.RetrofitClient


class MainActivity5 : AppCompatActivity() {

    //Declarar el Modelo y Componente de la vista
    //  private lateinit var sendModel: SendModel
    private lateinit var binding: ActivityMain5Binding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var viewModel: WalletViewModel

    //private lateinit var txtBalance: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //Singleton:misma instancia que MainActivity8
        // sendModel = SendModel

        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        val database = TransactionsDataBase.getDatabase(this)
        val repository = WalletRepository(database.transactionsDao(), RetrofitClient.instance)
        val factory = WalletViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WalletViewModel::class.java)

        setupButtons()

        loadData()
        viewModel.syncTransactions(123)

    }


    /*
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
    } */
    override fun onResume() {
        super.onResume()
        // txtBalance.text = "$${String.format("%.2f", sendModel.getSaldoTotal())}"
        loadData()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.adapter = transactionAdapter
    }

    private fun setupButtons() {
        binding.btnSend.setOnClickListener {
            startActivity(Intent(this, MainActivity8::class.java))
        }

        binding.btnReceive.setOnClickListener {
            startActivity(Intent(this, MainActivity7::class.java))
        }

        binding.imgProfile.setOnClickListener {
            startActivity(Intent(this, MainActivity9::class.java))
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            //val history = viewModel.getAllLocalTransactions()
            //transactionAdapter.updateList(history)
            try {
                val history = viewModel.getAllLocalTransactions()
                transactionAdapter.updateList(history)


                val total = history.sumOf { it.amount }
                binding.txtBalance.text = "$${String.format("%.2f", total)}"
            } catch (e: Exception) {

            }


        }
    }
}

