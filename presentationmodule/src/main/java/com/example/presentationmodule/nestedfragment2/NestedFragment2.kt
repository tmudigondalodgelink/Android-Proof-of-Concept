package com.example.presentationmodule.nestedfragment2

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.presentationmodule.databinding.FragmentNestedFragment2Binding

class NestedFragment2 : Fragment() {
    private var _binding: FragmentNestedFragment2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: NestedFragment2ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNestedFragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.counterButton.setOnClickListener {
            viewModel.increaseCount()
        }
    }

    private fun setupObservers() {
        viewModel.count.observe(viewLifecycleOwner) { count ->
            binding.counterView.text = count.toString()
        }
    }
}
