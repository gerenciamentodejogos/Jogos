package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.gerenciamentodejogos.R

import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.modelos.*
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_princ_prox_jogo.*
import java.text.SimpleDateFormat


class FragmentoDetalhesResultado : Fragment() {
    private lateinit var VMResultados: ResultadosViewModel

    private var lineares: MutableList<LinearLayout> = mutableListOf()
    //private var dezenas: MutableList<TextView> = mutableListOf()
    private var dezenas2: MutableList<CardView> = mutableListOf()

    private var tipoJogo: Int = 0
    private var numeroConcurso:Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            numeroConcurso = it.getInt(NUMERO_CONCURSO)
            tipoJogo = it.getInt(TIPO_JOGO)
        }

        activity?.let {
            VMResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
        }

        tipoJogo = VMResultados.tipoJogoAtual.value?: 0

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        criarDezenas()
        adicionarDezenas()
        configurarVMResultados()

        activity?.let {
            VMResultados.tipoJogoAtual.observe(it, Observer {
                //configurarVMResultados()
            })
        }
    }

    private fun configurarVMResultados() {
        activity?.let {
            //VMResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
            val jogo = VMResultados.getJogo(tipoJogo, numeroConcurso)
            if (jogo == null) {
                DownloadTask().execute()
            } else {
                atualizarDadosNaTela(jogo)
            }
        }
    }

    private fun adicionarDezenas() {
        for (l in lineares) {
            linearlayout_container_res.addView(l)
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

//        textViewDezena = layoutInflater.inflate(R.layout.textview_dezena, linear,false) as TextView
        textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear,false) as CardView
        //textViewDezena.text = dezenas.count().plus(1).toString()
//        dezenasPorLinha = calcularDezenasQueCabem(linear, textViewDezena)
        dezenasPorLinha = 5
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
//        dezenas.add(textViewDezena)
        dezenas2.add(textViewDezena)

        for (l in 2..quantasLinhas) {
            linear = layoutInflater.inflate(R.layout.linearlayout_dezenas, linearlayout_container_res, false) as LinearLayout
            lineares.add(linear)
        }

        for (d in 2..dezenasTotal) {
            textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear, false) as CardView
//            dezenas.add(textViewDezena)
            dezenas2.add(textViewDezena)
        }

        var dezenasAdicionadas = 0

        for (lin in lineares) {
            for (d in 1..dezenasPorLinha) {
                lin.addView(dezenas2[dezenasAdicionadas++])
                if (dezenasAdicionadas == dezenasTotal) break
            }
        }
    }

    private fun calcularDezenasQueCabem(linear: LinearLayout, textView: TextView): Int {
        //TODO - OBTER TAMANHO DA TELA E DOS ELEMENTOS LINEARLAYOUT E TEXTVIEW DAS DEZENAS

        return 5
    }

    private fun atualizarDadosNaTela(jogo: Jogo) {
        textView_ganhadores.setTextColor(jogo.corPrimaria)
        textView_concurso.setTextColor(jogo.corPrimaria)

        //TODO -  RETIRAR '&& jogo.concurso < PROXIMO_CONCURSO[tipoJogo]'
        if (jogo.encerrado && jogo.concurso < PROXIMO_CONCURSO[tipoJogo]) {
            //adicionarDezenas()


            textView_ganhadores.text = if (jogo.acumulou) {
                 getText(R.string.texto_acumulou)
            } else {
                "${jogo.ganhadores.toString()} GANHADORES!"
            }
            textView_ganhadores.visibility = View.VISIBLE

            try {
                if (jogo is MegaSena) {
                    for (d in 0..jogo.resultadoOrdenado.count() - 1) {
                        val text = dezenas2[d].findViewById<TextView>(R.id.tv_dezena)
                        text.text = jogo.resultadoOrdenado[d]
                        text.setBackgroundColor(jogo.corPrimaria)
                    }
                }

                if (jogo is Quina) {
                    for (d in 0..jogo.resultadoOrdenado.count() - 1) {
                        val text = dezenas2[d].findViewById<TextView>(R.id.tv_dezena)
                        text.text = jogo.resultadoOrdenado[d]
                        text.setBackgroundColor(jogo.corPrimaria)
                    }
                }
            } catch (erro: Exception) {

            }

            textView_concurso.text = jogo.concurso.toString()
            textView_data_sorteio.text = formatarData(jogo.dataConcurso)
            textView_local_sorteio.text = "${jogo.cidadeSorteio} - ${jogo.ufSorteio}\n${jogo.localSorteio}"
        } else {
            //CARREGAR INFORMAÇÕES DO PRÓXIMO CONCURSO
            textView_concurso.text = "PROXIMO CONCURSO"
            textView_data_sorteio.text = ""
            textView_ganhadores.visibility = View.GONE
        }

        progressBar_detalhes_res.visibility = View.GONE
    }

//    inner class CriarDezenas: AsyncTask<Unit, Unit, Unit>() {
//        override fun doInBackground(vararg params: Unit?) {
//            criarDezenas()
//        }
//    }
    inner class DownloadTask: AsyncTask<Unit, Unit, Jogo>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar_detalhes_res.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Unit?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled) {
                try {
                    result = InstanciarJogo().get(numeroConcurso, tipoJogo, resources)
                } catch (e: Exception) {
                    Log.d("ERRO", e.message)
                    e.message
                }
            }
            return result
        }

        override fun onPostExecute(result: Jogo?) {
            result?.let {
                VMResultados.adicionarJogo(it)
                atualizarDadosNaTela(it)
            }
        }
    }

    private fun formatarData(valor: Long): String {
        return  SimpleDateFormat("dd/MM/yyyy").format(valor)
    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: Int): FragmentoDetalhesResultado {
            val fragmentoDetalhesResultado = FragmentoDetalhesResultado()
            fragmentoDetalhesResultado.arguments = Bundle().apply {
                putInt(NUMERO_CONCURSO, numConcurso)
                putInt(TIPO_JOGO, tipoJogo)
            }
            return fragmentoDetalhesResultado
        }
    }
}
