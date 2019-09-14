package com.example.gerenciamentodejogos.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Jogo
import kotlinx.android.synthetic.main.resultado_loteca_lotogol.view.*

class LotecaLotogolAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens: List<Jogo.ResultadoLotecaLotogol> = listOf()

    fun alterarDados(novosItens: List<Jogo.ResultadoLotecaLotogol>){
        this.itens = novosItens
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itens.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.resultado_loteca_lotogol, parent, false)
        return LotecaLotogolViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LotecaLotogolViewHolder) {
            val jogo = itens[position]

            holder.numJogo.text = position.plus(1).toString()
            holder.diaDaSemana.text = jogo.diaDaSemana
            holder.time1.text = jogo.time1
            holder.time2.text = jogo.time2
            holder.gols_time_1.text = jogo.gols_time1.toString()
            holder.gols_time_2.text = jogo.gols_time2.toString()

            if (position == itemCount - 1) {
                holder.divider.visibility = View.GONE
            }
        }
    }

    class LotecaLotogolViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val numJogo: TextView = itemView.findViewById(R.id.textView_num_jogo)
        val diaDaSemana: TextView = itemView.findViewById(R.id.textView_dia_da_semana)
        val time1: TextView = itemView.findViewById(R.id.textView_time_1)
        val time2: TextView = itemView.findViewById(R.id.textView_time_2)
        val gols_time_1: TextView = itemView.findViewById(R.id.textView_gols_time_1)
        val gols_time_2: TextView = itemView.findViewById(R.id.textView_gols_time_2)

        val divider: View = itemView.findViewById(R.id.divider_loteca_lotogol)
    }
}