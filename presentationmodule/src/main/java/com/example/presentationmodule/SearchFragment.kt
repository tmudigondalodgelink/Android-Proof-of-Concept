package com.example.presentationmodule

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.presentationmodule.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()
    var navigateToNestedFragment1: () -> Unit = {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
                navigateToNestedFragment1()
            }
        }
    }

    private fun setupObservers() {
        viewModel.count.observe(viewLifecycleOwner) { count ->
            binding.counterView.text = count.toString()
        }
    }
}
