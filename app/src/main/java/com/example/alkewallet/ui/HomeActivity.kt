package com.example.alkewallet.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.alkewallet.R
import com.example.alkewallet.controller.SessionManager
import com.example.alkewallet.controller.TransactionAdapter
import com.example.alkewallet.controller.WalletRepository
import com.example.alkewallet.data.api.RetrofitClient
import com.example.alkewallet.data.network.TransactionsDataBase
import com.example.alkewallet.databinding.ActivityMain5Binding
import com.example.alkewallet.viewmodel.WalletViewModel
import com.example.alkewallet.viewmodel.WalletViewModelFactory
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain5Binding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var viewModel: WalletViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        val database = TransactionsDataBase.getDatabase(this)
        val repository = WalletRepository(database.transactionsDao(), RetrofitClient.instance)
        val factory = WalletViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[WalletViewModel::class.java]

        setupProfileImage()
        setupObservers()
        setupButtons()

        viewModel.syncTransactions()
    }

    override fun onResume() {
        super.onResume()
        setupProfileImage()
        viewModel.loadLocalTransactions()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.adapter = transactionAdapter
    }

    private fun setupProfileImage() {
        val imageUrl = sessionManager.getProfileImageUrl()

        if (imageUrl.isNotBlank()) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.icon_profile)
                .error(R.drawable.icon_profile)
                .into(binding.imgProfile)
        } else {
            binding.imgProfile.setImageResource(R.drawable.icon_profile)
        }
    }

    private fun setupObservers() {
        viewModel.transactions.observe(this) { history ->
            transactionAdapter.updateList(history)
        }

        viewModel.balance.observe(this) { total ->
            binding.txtBalance.text = "$${String.format("%.2f", total)}"
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupButtons() {
        binding.btnSend.setOnClickListener {
            val balanceText = binding.txtBalance.text.toString()
            val balanceValue = balanceText
                .replace("$", "")
                .replace(",", "")
                .trim()
                .toDoubleOrNull() ?:0.0
            val intent = Intent(this, SendMoneyActivity::class.java)
            intent.putExtra("current_balance", balanceValue)
            startActivity(intent)
        }

        binding.btnReceive.setOnClickListener {
            startActivity(Intent(this, ReceiveMoneyActivity::class.java))
        }

        binding.imgProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}