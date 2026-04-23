package com.example.alkewallet.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.databinding.ItemTransactionBinding

class TransactionAdapter(private var list: List<TransactionsModelRoom>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        with(holder.binding) {
            txtItemSender.text = "De: ${item.sender} a ${item.receiver}"
            txtItemAmount.text = "Monto: $${String.format("%.2f", item.amount)}"
            txtItemDate.text = "Fecha: ${item.date}"
            txtItemDescription.text =
                "Descripción: ${if (item.description.isNotBlank()) item.description else "Sin descripción"}"
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<TransactionsModelRoom>) {
        list = newList
        notifyDataSetChanged()
    }
}