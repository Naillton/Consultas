package com.nailton.consultas.screens

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var consultaAdapter: ConsultaAdapter

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
        getMovies()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getMovies() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val result = viewModel.getConsultas()
            val consultaAdapter = ConsultaAdapter()
            recyclerView.adapter = consultaAdapter
            progressBar.visibility = View.VISIBLE
            result.observe(viewLifecycleOwner) {
                if (it != null) {
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