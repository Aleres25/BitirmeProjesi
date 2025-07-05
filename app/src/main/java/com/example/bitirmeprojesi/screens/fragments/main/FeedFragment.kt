package com.example.bitirmeprojesi.screens.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapter.ProductAdapter
import com.example.bitirmeprojesi.databinding.FragmentFeedBinding
import com.example.bitirmeprojesi.viewmodel.CartViewModel
import com.example.bitirmeprojesi.viewmodel.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_layout_animation)
        binding.feedRecyclerView.layoutAnimation = controller

        FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            FirebaseDatabase.getInstance().getReference("Users").child(uid).child("name")
                .get().addOnSuccessListener { snapshot ->
                    val userName = snapshot.getValue(String::class.java) ?: return@addOnSuccessListener


                    productAdapter = ProductAdapter(
                        emptyList(),
                        onItemClick = { selectedProduct ->
                            val bundle = Bundle().apply {
                                putSerializable("product", selectedProduct)
                            }
                            findNavController().navigate(R.id.action_feedFragment_to_detailFragment, bundle)
                        },
                        onAddToCart = { product, qty ->
                            cartViewModel.addToCart(
                                ad = product.ad,
                                resim = product.resim,
                                kategori = product.kategori,
                                fiyat = product.fiyat,
                                marka = product.marka,
                                siparisAdeti = qty,
                                kullaniciAdi = userName
                            )
                        }
                    )

                    binding.feedRecyclerView.adapter = productAdapter
                    binding.feedRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

                    viewModel.productList.observe(viewLifecycleOwner) {
                        productAdapter.updateList(it)
                        binding.feedRecyclerView.scheduleLayoutAnimation()
                    }


                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean = true
                        override fun onQueryTextChange(newText: String?): Boolean {
                            productAdapter.filter.filter(newText)
                            return true
                        }
                    })

                    viewModel.loadProducts()
                }
        }
    }


}