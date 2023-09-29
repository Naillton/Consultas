package com.nailton.consultas.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nailton.consultas.R
import com.nailton.consultas.data.Consulta
import com.nailton.consultas.databinding.CardConsultaBinding

class ConsultaAdapter: RecyclerView.Adapter<ConsultaAdapter.MyViewHolder>(){
    class MyViewHolder(private var binding: CardConsultaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(consulta: Consulta) {
            binding.consulta = consulta
        }
    }

    private val consultaList = ArrayList<Consulta>()

    fun setList(consultas: List<Consulta>) {
        consultaList.clear()
        consultaList.addAll(consultas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(
            parent.context
        )
        val binding: CardConsultaBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.card_consulta,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return consultaList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val consulta: Consulta = consultaList[position]
        holder.bind(consulta)
    }
}