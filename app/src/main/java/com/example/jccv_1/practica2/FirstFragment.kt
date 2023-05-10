package com.example.jccv_1.practica2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.FirstFragmentBinding

// Definición de la clase FirstFragment, que extiende de Fragment y utiliza el layout first_fragment.xml
class FirstFragment : Fragment(R.layout.first_fragment) {
    private lateinit var binding: FirstFragmentBinding

    // Método onViewCreated, que se llama después de que la vista del fragmento haya sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        // Infla el layout utilizando la clase FirstFragmentBinding
        binding = FirstFragmentBinding.inflate(layoutInflater)
    }
}
