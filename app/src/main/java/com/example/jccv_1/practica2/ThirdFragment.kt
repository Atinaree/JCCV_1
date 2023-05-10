package com.example.jccv_1.practica2

import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.ThirdFragmentBinding
import android.app.AlertDialog

class ThirdFragment : Fragment(R.layout.third_fragment) {

    private lateinit var binding: ThirdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.third_fragment, container, false)
        val botonInfo = view.findViewById<Button>(R.id.button4)

        // Creamos un objeto StateListDrawable para definir diferentes estados del botón
        val stateListDrawable = StateListDrawable()

        // Añadimos un estado cuando el botón está presionado
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_pressed),
            resources.getDrawable(R.drawable.botoninfoazulpressed)
        )

        // Añadimos un estado por defecto (cuando el botón no está presionado)
        stateListDrawable.addState(intArrayOf(), resources.getDrawable(R.drawable.botoninfoazul))

        // Establecemos el StateListDrawable como fondo del botón
        botonInfo.background = stateListDrawable

        botonInfo.setOnClickListener {
            // Llamamos a la función showPopup() cuando se hace clic en el botón
            showPopup()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        // Inflamos el layout ThirdFragmentBinding
        binding = ThirdFragmentBinding.inflate(layoutInflater)
    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater

        // Inflamos el layout formatopop en el AlertDialog
        builder.setView(inflater.inflate(R.layout.formatopop, null))
        val dialog = builder.create()
        dialog.show()

        // Configuramos el botón de aceptar en el AlertDialog
        val botono = dialog.findViewById<Button>(R.id.botonAceptar)
        botono?.setOnClickListener() {
            dialog.dismiss()
        }
    }
}
