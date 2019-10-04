package br.com.dev.jogosdaloteria.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Resultado

class FederalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens: List<Resultado.ResultadoFederal> = listOf()

    fun alterarDados(novosItens: List<Resultado.ResultadoFederal>){
        val lista = mutableListOf(Resultado.ResultadoFederal("", "", ""))
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
                    if (position == itemCount - 1) {
                        holder.divisor.visibility = View.GONE
                    }
                }
            }
        }
    }

    class FederalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textDestino: TextView = itemView.findViewById(R.id.textView_destino)
        val textBilhete: TextView = itemView.findViewById(R.id.textView_bilhete)
        val textValorPremio: TextView = itemView.findViewById(R.id.textView_valor_premio)
        val divisor: View = itemView.findViewById(R.id.divider_federal)
    }
}