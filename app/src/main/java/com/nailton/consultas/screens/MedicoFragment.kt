package com.nailton.consultas.screens

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nailton.consultas.R
import com.nailton.consultas.data.Consulta
import com.nailton.consultas.databinding.FragmentMedicoBinding
import com.nailton.consultas.presentation.configmodel.MyViewModel
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class MedicoFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var viewModel: MyViewModel
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
                    Color.parseColor("#FFC55C"),
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

            updtFloating.setOnClickListener {
                updateConsultas()
            }

            ConsultaAdapter(::deleteQuery, ::updateQuery)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getConsultas()
        updateConsultas()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getConsultas() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val result = viewModel.getConsultas()
            val consultaAdapter = ConsultaAdapter(::deleteQuery, ::updateQuery)
            recyclerView.adapter = consultaAdapter
            progressBar.visibility = View.VISIBLE
            result.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    consultaAdapter.setList(it)
                    consultaAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(
                        context,
                        "Sem consultas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateConsultas() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val consultaAdapter = ConsultaAdapter(::deleteQuery, :: updateQuery)
            recyclerView.adapter = consultaAdapter
            val responseLiveData = viewModel.updateConsultas()
            progressBar.visibility = View.VISIBLE
            responseLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    consultaAdapter.setList(it)
                    consultaAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                    getConsultas()
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Sem consultas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
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

    @SuppressLint("NotifyDataSetChanged")
    fun deleteQuery(consulta: Consulta) {
        val result = viewModel.deleteQuery(consulta)
        result.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    context,
                    "Consulta deletada",
                    Toast.LENGTH_LONG
                ).show()
                updateConsultas()
            } else {
                Toast.makeText(
                    context,
                    "Nao foi possivel deletar a consulta",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun updateQuery(consulta: Consulta) {
        val bundle: Bundle = Bundle()
        bundle.putString("userId", consulta.userId)
        bundle.putString("pacienteEmail", consulta.pacienteEmail)
        bundle.putString("pacienteNome", consulta.pacienteNome)
        bundle.putString("titulo", consulta.titulo)
        bundle.putString("descricao", consulta.descricao)
        findNavController().navigate(R.id.action_medicoFragment_to_updateFragment, bundle)
    }
}