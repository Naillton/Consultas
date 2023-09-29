package com.nailton.consultas.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nailton.consultas.R
import com.nailton.consultas.databinding.FragmentMedicoBinding
import com.nailton.consultas.presentation.configmodel.MyViewModel
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class MedicoFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentMedicoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medico, container, false)
        (activity?.application as Injector).createConsultaSubComponent().injectMedico(this)

        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#4DA0F3"),
                    Color.parseColor("#8DC7F3"))
            );
            gradientDrawable.cornerRadius = 0f;
            medicoFragmentConstraint.background = gradientDrawable

            addFloating.setOnClickListener {
                navigateToCreateQuery()
            }

            outFloating.setOnClickListener {
                outApplication()
            }
        }
        return binding.root
    }

    private fun navigateToCreateQuery() {
        findNavController().navigate(R.id.action_medicoFragment_to_novaConsultaFragment)
    }

    private fun outApplication() {
        val result = viewModel.outApplication()
        result.observe(viewLifecycleOwner) {
            it?.signOut()
            findNavController().navigate(R.id.loginFragment)
        }
    }
}