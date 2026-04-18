package com.example.alkewallet.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alkewallet.data.network.TransactionsModelRoom
import com.example.alkewallet.databinding.ItemTransactionBinding

class TransactionAdapter(private var list: List<TransactionsModelRoom>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    // El ViewHolder ahora recibe el objeto de Binding en lugar de una View
    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflamos el binding del layout item_transaction
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // Usamos el binding para acceder directamente a las vistas por su ID
        with(holder.binding) {
            txtItemSender.text = "De: ${item.sender} a ${item.receiver}"
            txtItemAmount.text = "$${item.amount}"
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<TransactionsModelRoom>) {
        this.list = newList
        notifyDataSetChanged()
    }
}