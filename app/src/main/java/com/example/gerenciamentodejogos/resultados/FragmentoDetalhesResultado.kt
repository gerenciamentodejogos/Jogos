package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.gerenciamentodejogos.R

import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.modelos.Jogo
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_resultados.*


class FragmentoDetalhesResultado : Fragment() {
    private lateinit var resultadosViewModel: ResultadosViewModel
    private lateinit var dadosDoJogo: Jogo

    var numeroConcurso = 0
    var tipoJogo = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            resultadosViewModel = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        retainInstance = true
    }

    fun subscribe() {
        arguments?.let {
            numeroConcurso = it.getInt(NUMERO_CONCURSO)
            tipoJogo = it.getInt(TIPO_JOGO)
        }
        if (numeroConcurso != PROXIMO_CONCURSO[tipoJogo]) {
            DownloadTask().execute(numeroConcurso).get()
        } else {
            //CARREGAR INFORMAÇÕES DO PRÓXIMO CONCURSO
            textView_concurso.text = "PROXIMO CONCURSO"
            textView_data_sorteio.text = ""
            textView_ganhadores.text = ""
        }
    }

    private fun calcularDezenasQueCabem(): Int {
        return 1
    }

    private fun atualizarDadosNaTela(dadosJogo: Jogo) {
        with(dadosJogo) {
            if (dadosResultado.acumulado) {
                textView_acumulou.visibility = View.VISIBLE
            }

            val cor = Color.parseColor(resources.getStringArray(R.array.cores_primarias)[tipoJogo])

            val linearLayoutContainerRes = linearlayout_container_res

            val dezenasQueCabem = calcularDezenasQueCabem()
            val linhasNecessarias = dezenasSorteadas / dezenasQueCabem
            val dezenasPorLinha = dezenasSorteadas / linhasNecessarias

            var dezenasAdicionadas = 0
            for (linear in 1..linhasNecessarias) {
                val linearLayoutDezena = LayoutInflater.from(context).inflate(R.layout.linearlayout_dezenas, null)

                if (linearLayoutDezena is LinearLayout) {
                    for (d in 1..dezenasPorLinha) {
                        val textViewDezena = LayoutInflater.from(context).inflate(R.layout.textview_dezena, null)
                        if (textViewDezena is TextView) {
                            textViewDezena.setBackgroundColor(cor)
                            textViewDezena.text = dadosResultado.resultado[dezenasAdicionadas++].toString()
                            linearLayoutDezena.addView(textViewDezena)
                        }
                        if (dezenasAdicionadas == dezenasSorteadas) break
                    }
                }
                linearLayoutContainerRes.addView(linearLayoutDezena)
            }

            activity?.let {
                val textViewNomeJogo = it.findViewById<TextView>(R.id.textView_nome_jogo)
                textViewNomeJogo.setTextColor(cor)
            }

            textView_concurso.text = dadosResultado.concurso.toString()
            textView_data_sorteio.text = dadosResultado.data
            textView_ganhadores.text = dadosResultado.ganhadores.toString()
        }

    }

    inner class DownloadTask: AsyncTask<Int, Unit, Jogo>() {

        override fun doInBackground(vararg numConcursos: Int?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled && numConcursos.isNotEmpty()) {
                val concurso = numConcursos[0]?: 1
                try {
                    result = Jogo(concurso, tipoJogo, resources.getStringArray(R.array.url_jogos)[tipoJogo], getString(R.string.url_parametro_1), getString(R.string.url_parametro_2))
                } catch (e: Exception) {
                    Log.d("ERRO", e.message)
                    e.message
                }
            }
            return result
        }

        override fun onPostExecute(result: Jogo?) {
            result?.let {
                atualizarDadosNaTela(result)
            }
        }
    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: Int = 0): FragmentoDetalhesResultado {
            val fragmentoDetalhesResultado = FragmentoDetalhesResultado()
            fragmentoDetalhesResultado.arguments = Bundle().apply {
                putInt(NUMERO_CONCURSO, numConcurso)
                putInt(TIPO_JOGO, tipoJogo)
            }
            return fragmentoDetalhesResultado
        }
    }
}
