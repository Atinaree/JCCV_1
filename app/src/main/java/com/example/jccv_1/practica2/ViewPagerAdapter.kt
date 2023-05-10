package com.example.jccv_1.practica2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// Clase ViewPagerAdapter que extiende de FragmentPagerAdapter
class ViewPagerAdapter(fm: FragmentManager, var tabCount: Int) : FragmentPagerAdapter(fm) {

    // Metodo getItem que devuelve el fragmento correspondiente a una posicion dada
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment()  // Fragmento para la posición 0
            1 -> SecondFragment() // Fragmento para la posición 1
            2 -> ThirdFragment()  // Fragmento para la posición 2
            else -> FirstFragment() // Fragmento predeterminado, en caso de posición inválida
        }
    }

    // Metodo getCount que devuelve el numero total de fragmentos/tabulaciones
    override fun getCount(): Int {
        return tabCount
    }

    // Metodo getPageTitle que devuelve el título de la pagina/tabulacion en la posicion dada
    override fun getPageTitle(position: Int): CharSequence {
        return "Tab " + (position + 1)
    }
}
