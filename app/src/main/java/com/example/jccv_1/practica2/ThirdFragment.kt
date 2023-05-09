package com.example.jccv_1.practica2
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondFragmentBinding
import com.example.jccv_1.databinding.ThirdFragmentBinding


class ThirdFragment : Fragment(R.layout.third_fragment) {
    private lateinit var binding: ThirdFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.third_fragment, container, false)
        val botonInfo = view.findViewById<Button>(R.id.button4)

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), resources.getDrawable(R.drawable.botoninfoazulpressed))
        stateListDrawable.addState(intArrayOf(), resources.getDrawable(R.drawable.botoninfoazul))
        botonInfo.background = stateListDrawable

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }
    private fun setupData() {
        binding = ThirdFragmentBinding.inflate(layoutInflater)

    }
}