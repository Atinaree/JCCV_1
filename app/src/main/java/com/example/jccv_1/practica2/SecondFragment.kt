package com.example.jccv_1.practica2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondFragmentBinding

// Definición de la clase SecondFragment, que extiende de Fragment y utiliza el layout second_fragment.xml
class SecondFragment : Fragment(R.layout.second_fragment) {
    private lateinit var binding: SecondFragmentBinding

    // Método onViewCreated, que se llama después de que la vista del fragmento haya sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        // Infla el layout utilizando la clase SecondFragmentBinding
        binding = SecondFragmentBinding.inflate(layoutInflater)
    }
}