package com.example.jccv_1.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jccv_1.R
import com.example.jccv_1.databinding.TabberBinding
import com.google.android.material.tabs.TabLayout

class Actividad2 : AppCompatActivity() {

    private lateinit var binding: TabberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout utilizando el enlace generado por View Binding
        binding = TabberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el TabLayout y el ViewPager
        setupTabLayout()
        setupViewPager()

        // Evento que cierra la actividad el el boton atras
        binding.button7.setOnClickListener() {
            finish()
        }
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            // Establecer el adaptador personalizado para el ViewPager
            adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
            // Agregar un listener para detectar cambios de página en el ViewPager
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.apply {
            // Agregar pestañas al TabLayout
            addTab(this.newTab().setText(R.string.first_fragment))
            addTab(this.newTab().setText(R.string.second_fragment))
            addTab(this.newTab().setText(R.string.third_fragment))

            // Agregar un listener para detectar la selección de una pestaña
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // Obtener la posición de la pestaña seleccionada y establecerla como la página actual del ViewPager
                    tab?.position?.let {
                        binding.viewPager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Cuando se deselecciona una pestaña
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Cuando se vuelve a seleccionar una pestaña
                }
            })
        }
    }
}
