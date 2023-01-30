package com.example.ranfomfactnumbers.numbers.presentation

import android.os.Bundle
import android.view.View
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
        override fun click(item: NumberUi) = navigateToDetails(item)
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
        binding.editText.doAfterTextChanged { viewModel.clearError() }
        binding.getRandomFact.setOnClickListener { viewModel.fetchRandomNumberFact() }
    }

    private fun observeViewModel() {
        viewModel.observeList(this) {
            adapter.submitList(it) {
                binding.historyRecyclerView.scrollToPosition(0)
            }
        }
        viewModel.observeState(this) { it.apply(binding.inputEditText) }
        viewModel.observeProgress(this, binding.progress::setVisibility)
    }

    private fun navigateToDetails(item: NumberUi) {
        val action = NumbersFragmentDirections.toDetailsFragment(item.map(DetailsUI))
        findNavController().navigate(action)
    }

}