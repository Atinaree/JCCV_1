package com.example.jccv_1.practica2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.FirstFragmentBinding


class FirstFragment : Fragment(R.layout.first_fragment) {
    private lateinit var binding: FirstFragmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }
    private fun setupData() {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        binding.label.text = "Hola soy edu"
    }
}
