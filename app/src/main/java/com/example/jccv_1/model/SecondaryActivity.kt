package com.example.jccv_1.model
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.databinding.SecondaryActivityBinding
import java.util.*

class SecondaryActivity : Activity() { //...
    lateinit var binding: SecondaryActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


}}
