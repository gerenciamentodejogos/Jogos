package br.com.dev.jogosdaloteria.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Resultado

class LotecaLotogolAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens: List<Resultado.ResultadoLotecaLotogol> = listOf()

    fun alterarDados(novosItens: List<Resultado.ResultadoLotecaLotogol>){
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