package com.nailton.consultas.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nailton.consultas.R
import com.nailton.consultas.databinding.FragmentNovaConsultaBinding
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class NovaConsultaFragment : Fragment() {

    private var _binding: FragmentNovaConsultaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_nova_consulta, container, false)

        binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#4DA0F3"),
                    Color.parseColor("#8DC7F3"))
            );
            gradientDrawable.cornerRadius = 0f;
            newQuery.background = gradientDrawable
        }

        return binding.root
    }

}