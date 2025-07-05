package com.example.bitirmeprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bitirmeprojesi.databinding.RowProductBinding
import com.example.bitirmeprojesi.entitiy.Product

class ProductAdapter(private var fullList: List<Product>, private val onItemClick: (Product) -> Unit, private val onAddToCart: (Product, Int) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var filteredList: List<Product> = fullList.toList()
    private val productQuantities = mutableMapOf<Int, Int>()

    inner class ProductViewHolder(val binding: RowProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredList[position]
        val quantity = productQuantities[product.id] ?: 0

        with(holder.binding) {
            textViewName.text = product.ad
            textViewPrice.text = "₺${product.fiyat}"
            imageViewProduct.load("http://kasimadalan.pe.hu/urunler/resimler/${product.resim}")

            textViewQuantity.text = quantity.toString()

            buttonPlus.setOnClickListener {
                val current = productQuantities[product.id] ?: 0
                productQuantities[product.id] = current + 1
                textViewQuantity.text = productQuantities[product.id].toString()
            }

            buttonMinus.setOnClickListener {
                val current = productQuantities[product.id] ?: 0
                if (current > 0) {
                    productQuantities[product.id] = current - 1
                    textViewQuantity.text = productQuantities[product.id].toString()
                }
            }


            imageViewProduct.setOnClickListener {
                onItemClick(product)
            }
            buttonAddToCart.setOnClickListener {
                val currentQty = productQuantities[product.id] ?: 0
                if (currentQty > 0) {
                    onAddToCart(product, currentQty)
                }
            }
        }
    }

    fun updateList(newList: List<Product>) {
        fullList = newList
        filteredList = newList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val result = FilterResults()
                val searchText = query.toString().lowercase().trim()

                result.values = if (searchText.isEmpty()) {
                    fullList
                } else {
                    fullList.filter { it.ad.lowercase().contains(searchText) }
                }

                return result
            }

            override fun publishResults(query: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Product>
                notifyDataSetChanged()
            }
        }
    }
}
