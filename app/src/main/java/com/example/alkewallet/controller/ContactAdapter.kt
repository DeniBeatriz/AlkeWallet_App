package com.example.alkewallet.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alkewallet.databinding.ItemContactBinding
import com.example.alkewallet.model.UserModel

class ContactAdapter(private val onContactSelected: (String) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var contacts = listOf<UserModel>()

    fun setContacts(list: List<UserModel>) {
        this.contacts = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.txtNameContact.text = contact.name
        holder.binding.root.setOnClickListener { onContactSelected(contact.name) }
    }

    override fun getItemCount() = contacts.size
}