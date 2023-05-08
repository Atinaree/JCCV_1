package com.example.jccv_1.practica2
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondFragmentBinding
import com.example.jccv_1.databinding.ThirdFragmentBinding


class ThirdFragment : Fragment(R.layout.third_fragment) {
    private lateinit var binding: ThirdFragmentBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }
    private fun setupData() {
        binding = ThirdFragmentBinding.inflate(layoutInflater)

    }
}