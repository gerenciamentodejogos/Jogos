package br.com.dev.jogosdaloteria.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Jogo
import java.text.NumberFormat

class ValoresAdapter(var itens: List<Jogo.Valores> = mutableListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var cor = 0

    fun alterarDados(valores: List<Jogo.Valores>){
        itens = valores
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itens.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.detalhes_valores, parent, false)
        return ValoresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ValoresViewHolder) {
            val valor = itens[position]
            holder.textViewTextoValor.text = valor.texto
            holder.textViewValor.text = NumberFormat.getCurrencyInstance().format(valor.valor)
            holder.textViewValor.setTextColor(cor)
            if (position == itemCount - 1) {
                holder.divisor.visibility = View.GONE
            }
        }
    }
}

class ValoresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textViewTextoValor: TextView = itemView.findViewById(R.id.textView_texto_detalhes_valores)
    val textViewValor: TextView = itemView.findViewById(R.id.textView_valor_detalhes_valores)
    val divisor: View = itemView.findViewById(R.id.divider_valores)
}