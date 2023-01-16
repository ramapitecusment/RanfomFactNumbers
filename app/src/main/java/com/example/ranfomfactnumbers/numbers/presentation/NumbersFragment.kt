package com.example.ranfomfactnumbers.numbers.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.databinding.FragmentNumbersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NumbersFragment : Fragment(R.layout.fragment_numbers) {

    private val binding: FragmentNumbersBinding by viewBinding(FragmentNumbersBinding::bind)
    private val viewModel: NumbersViewModel by viewModel()

    private val clickListener = object : ClickListener {
        override fun click(item: NumberUi) = navigateToDetails()
    }

    private val adapter = NumbersAdapter(clickListener)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        observeViewModel()
        viewModel.init(savedInstanceState == null)
    }

    private fun bindView() {
        binding.historyRecyclerView.adapter = adapter
        binding.getFact.setOnClickListener {
            viewModel.fetchNumberFact(binding.editText.text.toString())
        }
        binding.getRandomFact.setOnClickListener { viewModel.fetchRandomNumberFact() }
        binding.editText.doAfterTextChanged { viewModel.clearError() }
    }

    private fun observeViewModel() {
        viewModel.observeState(this) { it.apply(binding.inputEditText) }
        viewModel.observeList(this, adapter::submitList)
        viewModel.observeProgress(this, binding.progress::setVisibility)
    }

    private fun navigateToDetails() {
        val action = NumbersFragmentDirections.toDetailsFragment("123")
        findNavController().navigate(action)
    }

}