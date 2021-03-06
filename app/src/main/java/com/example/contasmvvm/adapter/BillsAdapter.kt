package com.example.contasmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.example.contasmvvm.R
import com.example.contasmvvm.model.Bill

class ContasAdapter(val onItemClick: (Bill) -> Unit) : RecyclerView.Adapter<ContaViewHolder>() {

    private var listOfBills: MutableList<Bill> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conta, parent, false)
        return ContaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContaViewHolder, position: Int) {
        listOfBills[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { onItemClick(this) }
        }
    }

    override fun getItemCount(): Int = listOfBills.size

    fun refresh(newList: List<Bill>) {
        listOfBills = arrayListOf()
        listOfBills.addAll(newList)
        notifyDataSetChanged()
    }

}

class ContaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(bill: Bill) {
        setData(bill.uid, R.id.uidTextView)
        setData(bill.name, R.id.nameTextView)
        setData(bill.price.toString(), R.id.priceTextView)
    }

    private fun setData(value: String?, @IdRes componentId: Int) {
        itemView.findViewById<TextView>(componentId).apply {
            text = value
        }
    }

}