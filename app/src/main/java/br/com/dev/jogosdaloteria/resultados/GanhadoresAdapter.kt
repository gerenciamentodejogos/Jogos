package br.com.dev.jogosdaloteria.resultados

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Jogo


class GanhadoresAdapter(var items: List<Jogo.Ganhadores> = mutableListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recursos: Resources? = null

    fun alterarDados(ganhadores: List<Jogo.Ganhadores>){
        items = ganhadores
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        recursos = parent.context.resources
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.detalhes_ganhadores, parent, false)
        return GanhadoresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GanhadoresViewHolder) {
            val ganhador = items[position]

            holder.textViewLocalGanhadores.text = ganhador.getLocalGanhadores()
            holder.textViewQuantidadeGanhadores.text = textoPlural(ganhador.quantidade, R.plurals.texto_ganhador)

            if (position == itemCount - 1) {
                holder.divider.visibility = View.GONE
            }
        }
    }

    private fun textoPlural(quant: Int, idRecurso: Int, textoInicial: String = "", textoFinal: String = ""): String {
        val plural = recursos?.let {
            it.getQuantityString(idRecurso, quant)
        }
        return "${textoInicial}${quant} ${plural}${textoFinal}"
    }



    class GanhadoresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewLocalGanhadores: TextView = itemView.findViewById(R.id.textview_local_ganhadores)
        val textViewQuantidadeGanhadores: TextView = itemView.findViewById(R.id.textview_quantidade_ganhadores)
        val divider: View = itemView.findViewById(R.id.divider_ganhadores)
    }

}
