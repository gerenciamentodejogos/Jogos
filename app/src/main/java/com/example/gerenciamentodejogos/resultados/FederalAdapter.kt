package com.example.gerenciamentodejogos.resultados

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Jogo

class FederalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens: List<Jogo.ResultadoFederal> = listOf()

    fun alterarDados(novosItens: List<Jogo.ResultadoFederal>){
        this.itens = novosItens
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itens.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resultado_federal, parent, false)
        return FederalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FederalViewHolder) {

        }
    }

    class FederalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}