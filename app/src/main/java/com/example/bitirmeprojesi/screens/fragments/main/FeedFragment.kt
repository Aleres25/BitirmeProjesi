package com.example.bitirmeprojesi.screens.fragments.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapter.ProductAdapter
import com.example.bitirmeprojesi.databinding.FragmentFeedBinding
import com.example.bitirmeprojesi.viewmodel.CartViewModel
import com.example.bitirmeprojesi.viewmodel.InternetCheckViewModel
import com.example.bitirmeprojesi.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val internetCheckViewModel: InternetCheckViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()

        internetCheckViewModel.checkConnection()
        internetCheckViewModel.isConnected.observe(viewLifecycleOwner) { hasInternet ->
            if (hasInternet) {
                setupUI()
            } else {
                showNoInternetSnackbar()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        binding.gotoCart.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_cartFragment)
        }
    }

    private fun setupRecyclerView() {
        val animation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.recycler_layout_animation)
        binding.feedRecyclerView.layoutAnimation = animation
        binding.feedRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            internetCheckViewModel.checkConnection()
        }
    }

    private fun setupUI() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("name")

        userRef.get().addOnSuccessListener { snapshot ->
            val userName = snapshot.getValue(String::class.java) ?: return@addOnSuccessListener

            binding.textView.text = "Hoşgeldin $userName"

            productAdapter = ProductAdapter(emptyList(), onItemClick = { selectedProduct ->
                val bundle = Bundle().apply {
                    putSerializable("product", selectedProduct)
                }
                findNavController().navigate(R.id.action_feedFragment_to_detailFragment, bundle)
            }, onAddToCart = { product, qty ->
                cartViewModel.addToCart(ad = product.ad, resim = product.resim, kategori = product.kategori, fiyat = product.fiyat, marka = product.marka, siparisAdeti = qty, kullaniciAdi = userName)
            })

            binding.feedRecyclerView.adapter = productAdapter

            productViewModel.productList.observe(viewLifecycleOwner) {
                productAdapter.updateList(it)
                binding.feedRecyclerView.scheduleLayoutAnimation()
            }

            productViewModel.loadProducts()

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = true
                override fun onQueryTextChange(newText: String?): Boolean {
                    productAdapter.filter.filter(newText)
                    return true
                }
            })
        }
    }

    private fun showNoInternetSnackbar() {
        Snackbar.make(requireView(), "İnternet bağlantısı yok", Snackbar.LENGTH_INDEFINITE).setAction("Ayarlar") {
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivity(intent)
            }.setBackgroundTint("#222222".toColorInt()).setTextColor(Color.WHITE).setActionTextColor(Color.RED).show()
    }
}
