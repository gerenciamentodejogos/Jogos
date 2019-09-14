package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.modelos.TipoDeJogo
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragment_fragmento_resultado_info_principais.*
import kotlinx.android.synthetic.main.fragment_fragmento_resultado_info_principais.linearlayout_container_res
import kotlinx.android.synthetic.main.fragment_fragmento_resultado_info_principais.recyclerview_loteca_lotogol
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultadoInfoPrincipais : Fragment() {

    lateinit var VMResultados: ResultadosViewModel
    private var tipoJogo: Int = 0
    private var numConcurso: Int = 0

    private var lineares: MutableList<LinearLayout> = mutableListOf()
    private var dezenas: MutableList<CardView> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            tipoJogo = it.getInt(TIPO_JOGO)
            numConcurso = it.getInt(NUMERO_CONCURSO)
        }

        return inflater.inflate(R.layout.fragment_fragmento_resultado_info_principais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tipoJogo!= TipoDeJogo().LOTECA && tipoJogo!= TipoDeJogo().LOTOGOL) {
            criarDezenas()
            adicionarDezenas()
        }

        configurarRecyclerView()
    }

    private fun adicionarDezenas() {
        var itens = 2
        for (l in lineares) {
            linearlayout_container_res.addView(l, itens++ - 1)
        }
    }

    private fun criarDezenas() {
        var linear = layoutInflater.inflate(R.layout.linearlayout_dezenas, linearlayout_container_res, false) as LinearLayout
        lineares.add(linear)

        var textViewDezena: View

        val dezenasTotal = DadosDoJogo(tipoJogo, resources).dezenasSorteadas
        var dezenasPorLinha: Int// = 0
        val linhasNecessarias: Double// = 0.0
        val quantasLinhas: Int// = 0

        textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear,false) as CardView
        dezenasPorLinha = calcularDezenasQueCabem(linear, textViewDezena)
        linhasNecessarias = dezenasTotal.toDouble().div(dezenasPorLinha)

        quantasLinhas = if (linhasNecessarias - linhasNecessarias.toInt() != 0.0) {
            (linhasNecessarias - (linhasNecessarias - linhasNecessarias.toInt())).toInt() + 1
        } else {
            linhasNecessarias.toInt()
        }

        val dezenasLinhas = dezenasTotal.toDouble().div(quantasLinhas)

        dezenasPorLinha = if (dezenasLinhas - dezenasLinhas.toInt() != 0.0) {
            dezenasLinhas.toInt() + 1
        } else {
            dezenasLinhas.toInt()
        }
        dezenas.add(textViewDezena)

        for (l in 2..quantasLinhas) {
            linear = layoutInflater.inflate(R.layout.linearlayout_dezenas, linearlayout_container_res, false) as LinearLayout
            lineares.add(linear)
        }

        for (d in 2..dezenasTotal) {
            textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear, false) as CardView
            dezenas.add(textViewDezena)
        }

        var dezenasAdicionadas = 0

        for (lin in lineares) {
            for (d in 1..dezenasPorLinha) {
                lin.addView(dezenas[dezenasAdicionadas++])
                if (dezenasAdicionadas == dezenasTotal) break
            }
        }
    }

    private fun calcularDezenasQueCabem(linear: LinearLayout, cardView: CardView): Int {
        //TODO - OBTER TAMANHO DA TELA E DOS ELEMENTOS LINEARLAYOUT E TEXTVIEW DAS DEZENAS
        return 6
    }

    private fun configurarRecyclerView(){
        recyclerview_loteca_lotogol.adapter = LotecaLotogolAdapter()
        recyclerview_loteca_lotogol.layoutManager = LinearLayoutManager(context)
    }


    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: Int): FragmentoResultadoInfoPrincipais {
            val fragmentoPrincipalDetalhesResultado = FragmentoResultadoInfoPrincipais()
            fragmentoPrincipalDetalhesResultado.arguments = Bundle().apply {
                putInt(TIPO_JOGO, tipoJogo)
                putInt(NUMERO_CONCURSO, numConcurso)
            }
            return fragmentoPrincipalDetalhesResultado
        }
    }
}
