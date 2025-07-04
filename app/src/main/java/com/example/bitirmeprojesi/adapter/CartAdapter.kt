package com.example.bitirmeprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bitirmeprojesi.databinding.RowCartItemBinding
import com.example.bitirmeprojesi.entitiy.CartItem

class CartAdapter(
    private var cartList: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: RowCartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

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

            // Şimdilik butonlar işlevsiz sadece görüntüde kalsın
            buttonCartPlus.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Güncelleme yakında aktif olacak", Toast.LENGTH_SHORT).show()
            }

            buttonCartMinus.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Güncelleme yakında aktif olacak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateList(newList: List<CartItem>) {
        cartList = newList
        notifyDataSetChanged()
    }
}

