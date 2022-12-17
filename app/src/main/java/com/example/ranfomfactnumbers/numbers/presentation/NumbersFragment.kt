package com.example.ranfomfactnumbers.numbers.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.databinding.FragmentNumbersBinding

class NumbersFragment : Fragment(R.layout.fragment_numbers) {

    private val binding: FragmentNumbersBinding by viewBinding(FragmentNumbersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.getFact.setOnClickListener { navigateToDetails() }
    }

    private fun navigateToDetails() {
        val action = NumbersFragmentDirections.toDetailsFragment("123")
        findNavController().navigate(action)
    }

}