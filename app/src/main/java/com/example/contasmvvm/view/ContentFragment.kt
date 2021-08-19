package com.example.contasmvvm.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.contasmvvm.DetailActivity
import com.example.contasmvvm.R
import com.example.contasmvvm.adapter.ContasAdapter
import com.example.contasmvvm.model.Bill
import com.example.contasmvvm.viewmodel.ContentViewModel

class ContentFragment : Fragment(R.layout.content_fragment) {

    companion object {
        fun newInstance() = ContentFragment()
    }

    private lateinit var viewModel: ContentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val adapter = ContasAdapter() {

        Intent(requireContext(), DetailActivity::class.java).apply {
//            putExtras("bill_id", )
            startActivity(this)
        }
    }

    val observerContas = Observer<List<Bill>> {
        adapter.refresh(it)
        swipeRefreshLayout.isRefreshing = false
    }

    val observerError = Observer<String> {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)

        swipeRefreshLayout = view.findViewById(R.id.swipeContainer)
        recyclerView = view.findViewById<RecyclerView>(R.id.contasRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.contas.observe(viewLifecycleOwner, observerContas)

        view.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val inputName = view.findViewById<EditText>(R.id.billEditText)
            val inputPrice = view.findViewById<EditText>(R.id.valueEditText)
            if (inputName.text.toString().isNotEmpty() && inputPrice.text.toString().isNotEmpty()) {
                viewModel.addBill(
                    inputName.text.toString(),
                    inputPrice.text.toString().toDoubleOrNull()
                )
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        loadData()

    }

    fun loadData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.fetchBills()
    }

}