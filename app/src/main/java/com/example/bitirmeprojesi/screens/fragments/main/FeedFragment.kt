package com.example.bitirmeprojesi.screens.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapter.ProductAdapter
import com.example.bitirmeprojesi.databinding.FragmentFeedBinding
import com.example.bitirmeprojesi.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.productList.observe(viewLifecycleOwner) {
            binding.feedRecyclerView.adapter = ProductAdapter(it)
        }
        binding.feedRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.loadProducts()
    }
}