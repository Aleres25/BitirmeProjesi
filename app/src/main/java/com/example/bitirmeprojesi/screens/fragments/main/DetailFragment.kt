package com.example.bitirmeprojesi.screens.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentDetailBinding
import com.example.bitirmeprojesi.entitiy.Product
import com.example.bitirmeprojesi.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val cartViewModel: CartViewModel by viewModels()

    private var quantity = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getSerializable("product") as? Product
        product?.let {
            with(binding) {
                detailName.text = it.ad
                detailBrand.text = "Marka: ${it.marka}"
                detailCategory.text = "Kategori: ${it.kategori}"
                detailPrice.text = "₺${it.fiyat}"
                detailImageView.load("http://kasimadalan.pe.hu/urunler/resimler/${it.resim}")
            }

        }

        with(binding) {
            detailButtonPlus.setOnClickListener {
                quantity++
                detailTextQuantity.text = quantity.toString()
            }

            detailButtonMinus.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    detailTextQuantity.text = quantity.toString()
                }
            }

            buttonAddToCart.setOnClickListener {
                product?.let { product ->
                    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

                    currentUserUid?.let { uid ->
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("name")

                        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val userName = snapshot.getValue(String::class.java)
                                if (!userName.isNullOrEmpty()) {
                                    Log.d("SepetKontrol", "Ürün adı: ${product.ad}")
                                    Log.d("SepetKontrol", "Resim: ${product.resim}")
                                    Log.d("SepetKontrol", "Kategori: ${product.kategori}")
                                    Log.d("SepetKontrol", "Fiyat: ${product.fiyat}")
                                    Log.d("SepetKontrol", "Marka: ${product.marka}")
                                    Log.d("SepetKontrol", "Adet: $quantity")
                                    Log.d("SepetKontrol", "Kullanıcı Adı: $userName")

                                    cartViewModel.addToCart(ad = product.ad, resim = product.resim, kategori = product.kategori, fiyat = product.fiyat, marka = product.marka, siparisAdeti = quantity, kullaniciAdi = userName)
                                    findNavController().navigate(R.id.action_detailFragment_to_cartFragment)
                                } else {
                                    Log.e("SepetKontrol", "Kullanıcı adı boş!")
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("SepetKontrol", "Firebase hatası: ${error.message}")
                            }
                        })
                    } ?: Log.e("SepetKontrol", "Kullanıcı UID null")
                }
            }
        }
    }}