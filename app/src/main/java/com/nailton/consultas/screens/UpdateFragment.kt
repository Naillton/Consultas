package com.nailton.consultas.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nailton.consultas.R
import com.nailton.consultas.databinding.FragmentNovaConsultaBinding
import com.nailton.consultas.databinding.FragmentUpdateBinding
import com.nailton.consultas.presentation.configmodel.MyViewModel
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class UpdateFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update, container, false
        )
        (activity?.application as Injector).createConsultaSubComponent().injectUpdate(this)

        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#FFC55C"),
                    Color.parseColor("#8DC7F3"))
            );
            gradientDrawable.cornerRadius = 0f
            newQuery.background = gradientDrawable

            val userId = arguments?.getString("userId")
            val pacienteEmail = arguments?.getString("pacienteEmail")
            titulo.setText(arguments?.getString("titulo"))
            pacienteNome.setText(arguments?.getString("pacienteNome").toString())
            descricao.setText(arguments?.getString("descricao").toString())
            Log.i("TAGY", pacienteNome.toString().trim())
            Log.i("TAGY", titulo.toString().trim())
            Log.i("TAGY", descricao.toString().trim())
            btnUpdate.setOnClickListener {
                updateQuery(
                    userId!!,
                    pacienteEmail!!,
                    pacienteNome.text.toString(),
                    titulo.text.toString(),
                    descricao.text.toString()
                )
            }
        }
        return binding.root
    }

    private fun updateQuery(
        userId: String,
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String
    ) {
        val valid = validationCamps(pacienteNome, titulo, descricao)
        if (valid) {
            val result = viewModel.updateQuery(
                userId,
                pacienteEmail,
                pacienteNome,
                titulo,
                descricao
            )
            result.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        context,
                        "Consulta Atualizada",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.medicoFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Nao foi atualizar deletar a consulta",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun validationCamps(
        pacienteNome: String,
        titulo: String,
        descricao: String): Boolean {
        if (
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