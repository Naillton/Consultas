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
import com.nailton.consultas.databinding.FragmentLoginBinding
import com.nailton.consultas.presentation.configmodel.MyViewModel
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        (activity?.application as Injector).createConsultaSubComponent().inject(this)

        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#4DA0F3"),
                    Color.parseColor("#8DC7F3"))
            );
            gradientDrawable.cornerRadius = 0f;
            constraintLogin.background = gradientDrawable

            btnLogin.setOnClickListener {
                val email = edtTextEmail.text.toString()
                val password = edtTextPass.text.toString()
                loginWithEmailAndPassword(email, password)
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val result = viewModel.persistLogin()
        result.observe(viewLifecycleOwner) {
            if (it != null) {
                accessConsultas()
            }
        }
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        val validCamps = validationCamps(email, password)
        if (validCamps) {
            val result = viewModel.login(email, password)
            result.observe(viewLifecycleOwner) {
                if (it == true) {
                    accessConsultas()
                } else {
                    Toast.makeText(
                        context,
                        "Usuario não existe",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun validationCamps(email: String, password: String): Boolean {
        if (email.length < 11 || password.length < 8) {
            Toast.makeText(
                context,
                "Campos invalidos",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun accessConsultas() {
        findNavController().navigate(R.id.action_loginFragment_to_medicoFragment)
    }

}