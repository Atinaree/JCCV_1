package com.example.jccv_1.practica2
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondFragmentBinding


class SecondFragment : Fragment(R.layout.second_fragment) {
    private lateinit var binding: SecondFragmentBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }
    private fun setupData() {
        binding = SecondFragmentBinding.inflate(layoutInflater)
        binding.label.text = "Second Frament"
    }
}