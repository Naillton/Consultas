package com.nailton.consultas.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nailton.consultas.R
import com.nailton.consultas.databinding.FragmentNovaConsultaBinding
import com.nailton.consultas.presentation.configmodel.MyViewModel
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class NovaConsultaFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentNovaConsultaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_nova_consulta, container, false)
        (activity?.application as Injector).createConsultaSubComponent().injectQuery(this)

        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#4DA0F3"),
                    Color.parseColor("#8DC7F3"))
            );
            gradientDrawable.cornerRadius = 0f
            newQuery.background = gradientDrawable

            btnQuery.setOnClickListener {
                val email = pacienteEmail.text.trim().toString()
                val nome = pacienteNome.text.trim().toString()
                val title = titulo.text.trim().toString()
                val desc = descricao.text.trim().toString()
                createQuery(
                    email,
                    nome,
                    title,
                    desc)
            }
        }

        return binding.root
    }

    private fun createQuery(
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String) {
        val result = viewModel.createQuery(
            pacienteEmail,
            pacienteNome,
            titulo,
            descricao
        )
        val valid = validationCamps(
            pacienteEmail,
            pacienteNome,
            titulo,
            descricao
        )
        if (valid) {
            result.observe(viewLifecycleOwner) {
                if (it == true) {
                    Toast.makeText(
                        context,
                        "Consulta Criada",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.medicoFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Erro ao criar consulta",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun validationCamps(
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String): Boolean {
        if (
            pacienteEmail.length < 11 ||
            pacienteNome.length < 3 ||
            titulo.length < 6 ||
            descricao.length < 10) {
            Toast.makeText(
                context,
                "Campos invalidos",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

}