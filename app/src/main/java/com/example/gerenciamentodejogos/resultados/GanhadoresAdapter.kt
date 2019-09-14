package com.example.gerenciamentodejogos.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Jogo


class GanhadoresAdapter(var items: List<Jogo.Ganhadores> = mutableListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun alterarDados(ganhadores: List<Jogo.Ganhadores>){
        items = ganhadores
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.detalhes_ganhadores, parent, false)
        return GanhadoresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GanhadoresViewHolder) {
            val ganhador = items[position]

            holder.textViewLocalGanhadores.text = if (ganhador.meioEletronico) {
                "Canal Eletrônico"
            } else {
                "${ganhador.cidade} - ${ganhador.uf}"
            }
            holder.textViewQuantidadeGanhadores.text = "${ganhador.quantidade} ganhadores"

            if (position == itemCount - 1) {
                holder.divider.visibility = View.GONE
            }
        }
    }

    class GanhadoresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewLocalGanhadores: TextView = itemView.findViewById(R.id.textview_local_ganhadores)
        val textViewQuantidadeGanhadores: TextView = itemView.findViewById(R.id.textview_quantidade_ganhadores)
        val divider: View = itemView.findViewById(R.id.divider_ganhadores)
    }

}
