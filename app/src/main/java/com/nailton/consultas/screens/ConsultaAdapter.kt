package com.nailton.consultas.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nailton.consultas.R
import com.nailton.consultas.data.Consulta
import com.nailton.consultas.databinding.CardConsultaBinding
import kotlin.reflect.KFunction1

class ConsultaAdapter(
    val delete: KFunction1<Consulta, Unit>,
    val update: KFunction1<Consulta, Unit>
) : RecyclerView.Adapter<ConsultaAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: CardConsultaBinding): RecyclerView.ViewHolder(binding.root) {
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
        holder.binding.apply {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                intArrayOf(
                    Color.parseColor("#39cb61"),
                    Color.parseColor("#45eca3"))
            );
            gradientDrawable.cornerRadius = 0f;
            constraintCard.background = gradientDrawable
            btnDel.setOnClickListener {
                delete(consulta)
            }

            constraintCard.setOnClickListener {
                update(consulta)
            }

            btnShare.setOnClickListener {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(consulta.pacienteEmail))
                    putExtra(Intent.EXTRA_SUBJECT, consulta.titulo)
                    putExtra(Intent.EXTRA_TEXT, "CONSULTA: ${consulta.descricao}")
                    type = "text/plain"
                }, null)
                it.context.startActivity(share)
            }
        }
        holder.bind(consulta)
    }
}