package br.com.dev.jogosdaloteria.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Resultado
import java.text.NumberFormat

class PremiacoesAdapter(var items: List<Resultado.Premiacoes> = mutableListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun alterarDados(premiacoes: List<Resultado.Premiacoes>){
        items = premiacoes
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.detalhes_premiacoes, parent, false)
        return PremiacoesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PremiacoesViewHolder) {
            val premiacao = items[position]

            holder.textViewFaixaPremiacao.text = premiacao.textoFaixa

            if (premiacao.numGanhadores == 0) {
                holder.textViewGanhadores.text = "Não houve ganhadores!"

                holder.textViewPremioIndividual.visibility = View.GONE
                holder.textViewTotalPago.visibility = View.GONE
                holder.textViewLabelPremioIndividual.visibility = View.GONE
                holder.textViewLabelTotalPago.visibility = View.GONE
            } else {
                holder.textViewGanhadores.text = "${premiacao.numGanhadores.toString()} ganhadores"
                holder.textViewPremioIndividual.text = NumberFormat.getCurrencyInstance().format(premiacao.valorIndividual)
                holder.textViewTotalPago.text = NumberFormat.getCurrencyInstance().format(premiacao.totalPago)
            }
        }
    }

    class PremiacoesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewFaixaPremiacao: TextView = itemView.findViewById(R.id.textview_faixa_premiacao)

        val textViewGanhadores: TextView = itemView.findViewById(R.id.textview_premiacoes_ganhadores)

        val textViewLabelPremioIndividual: TextView = itemView.findViewById(R.id.textview_label_premio_individual)
        val textViewPremioIndividual: TextView = itemView.findViewById(R.id.textview_premio_individual)

        val textViewLabelTotalPago: TextView = itemView.findViewById(R.id.textview_label_total_pago)
        val textViewTotalPago: TextView = itemView.findViewById(R.id.textview_total_pago)
    }

}

