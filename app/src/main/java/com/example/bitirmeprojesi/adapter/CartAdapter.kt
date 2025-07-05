package com.example.bitirmeprojesi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bitirmeprojesi.databinding.RowCartItemBinding
import com.example.bitirmeprojesi.entitiy.CartItem

class CartAdapter(private var cartList: List<CartItem>, private val onQuantityChange: (CartItem, Int) -> Unit, private val onDelete: (Int, String) -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: RowCartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = RowCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        with(holder.binding) {

            imageViewCart.load("http://kasimadalan.pe.hu/urunler/resimler/${item.resim}")
            textViewCartName.text = item.ad
            textViewCartQuantity.text = "Adet: ${item.siparisAdeti}"
            textViewQuantityCount.text = item.siparisAdeti.toString()


            buttonCartPlus.setOnClickListener {
                val newQty = item.siparisAdeti + 1
                onQuantityChange(item, newQty)
            }

            buttonCartMinus.setOnClickListener {
                val newQty = item.siparisAdeti - 1
                if (newQty <= 0) {
                    onDelete(item.sepetId, item.kullaniciAdi)
                } else {
                    onQuantityChange(item, newQty)
                }
            }
        }
    }

    fun updateList(newList: List<CartItem>) {
        cartList = newList
        notifyDataSetChanged()
    }
}

