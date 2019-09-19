package com.example.gerenciamentodejogos.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Jogo

class FederalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens: List<Jogo.ResultadoFederal> = listOf()

    fun alterarDados(novosItens: List<Jogo.ResultadoFederal>){
        val lista = mutableListOf(Jogo.ResultadoFederal("", "", ""))
//        novosItens.forEach {lista.add(it)}
        lista.addAll(novosItens)
        itens = lista
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            0
        } else {
            1
        }
//        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = itens.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resultado_federal, parent, false)
        return FederalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            if (holder is FederalViewHolder) {
                if (position > 0) {
                    val resultado = itens[position]
                    holder.textDestino.text = resultado.destino
                    holder.textBilhete.text = resultado.bilhete
                    holder.textValorPremio.text = resultado.valor
                }
            }
        }
    }

    class FederalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textDestino: TextView = itemView.findViewById(R.id.textView_destino)
        val textBilhete: TextView = itemView.findViewById(R.id.textView_bilhete)
        val textValorPremio: TextView = itemView.findViewById(R.id.textView_valor_premio)
    }
}