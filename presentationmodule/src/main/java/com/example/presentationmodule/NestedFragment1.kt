package com.example.presentationmodule

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.presentationmodule.databinding.FragmentNestedFragment1Binding

class NestedFragment1 : Fragment() {
    private var _binding: FragmentNestedFragment1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: NestedFragment1ViewModel by viewModels()
    var navigateToNestedFragment2: () -> Unit = {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNestedFragment1Binding.inflate(inflater, container, false)
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
        binding.apply {
            counterButton.setOnClickListener {
                viewModel.increaseCount()
            }

            navigateButton.setOnClickListener {
                navigateToNestedFragment2()
            }
        }
    }

    private fun setupObservers() {
        viewModel.count.observe(viewLifecycleOwner) { count ->
            binding.counterView.text = count.toString()
        }
    }
}
