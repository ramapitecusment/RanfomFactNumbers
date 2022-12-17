package com.example.ranfomfactnumbers.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.details.text = args.text
    }

}