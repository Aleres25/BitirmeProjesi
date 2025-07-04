package com.example.bitirmeprojesi.screens.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.adapter.CartAdapter
import com.example.bitirmeprojesi.databinding.FragmentCartBinding
import com.example.bitirmeprojesi.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Adapter
        adapter = CartAdapter(emptyList())


        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        // Observer
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items)
            Log.d("Cart", "Sepet verisi geldi: ${items.size} ürün")
        }


        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            database.reference.child("Users").child(uid).child("name").get()
                .addOnSuccessListener { snapshot ->
                    val username = snapshot.value as? String
                    username?.let {
                        viewModel.loadCart(it)
                        Log.d("Cart", "Kullanıcı adı: $it")
                    } ?: Log.e("Cart", "Kullanıcı adı null")
                }
                .addOnFailureListener {
                    Log.e("Cart", "Firebase hata: ${it.localizedMessage}")
                }
        }
    }
}

