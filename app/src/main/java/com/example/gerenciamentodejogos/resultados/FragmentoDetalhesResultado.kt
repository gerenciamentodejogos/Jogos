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
import com.example.gerenciamentodejogos.modelos.*
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_resultados.*
import kotlin.math.absoluteValue


class FragmentoDetalhesResultado : Fragment() {
    private lateinit var resultadosViewModel: ResultadosViewModel
    //private lateinit var dadosDoJogo: Jogo
    private lateinit var tipoJogo: TipoDeJogo

    var numeroConcurso = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            resultadosViewModel = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    fun subscribe() {
        arguments?.let {
            numeroConcurso = it.getInt(NUMERO_CONCURSO)
            tipoJogo = when (it.getInt(TIPO_JOGO)) {
                0 -> TipoDeJogo.MEGA_SENA
                1 -> TipoDeJogo.QUINA
                2 -> TipoDeJogo.LOTOFACIL
                3 -> TipoDeJogo.LOTOMANIA
                4 -> TipoDeJogo.DUPLA_SENA
                5 -> TipoDeJogo.FEDERAL
                6 -> TipoDeJogo.DIA_DE_SORTE
                7 -> TipoDeJogo.TIMEMANIA
                8 -> TipoDeJogo.LOTECA
                9 -> TipoDeJogo.LOTOGOL

                else ->  TipoDeJogo.MEGA_SENA
            }
        }
        if (numeroConcurso != PROXIMO_CONCURSO[tipoJogo.value]) {
            DownloadTask().execute(numeroConcurso).get()
        } else {
            //CARREGAR INFORMAÇÕES DO PRÓXIMO CONCURSO
            textView_concurso.text = "PROXIMO CONCURSO"
            textView_data_sorteio.text = ""
            textView_ganhadores.text = ""
        }
    }

    private fun calcularDezenasQueCabem(linear: View): Int {
        val textView = LayoutInflater.from(context).inflate(R.layout.textview_dezena, null) as TextView
        textView.text = "00"

        var larguraTotal  = 0
        if (linear is LinearLayout) {
            larguraTotal += linear.width
        }

        if (textView is TextView) {
            larguraTotal += textView.width
        }

        return larguraTotal
    }

    private fun atualizarDadosNaTela(jogo: Jogo) {
        with(jogo) {
            if (acumulou) {
                textView_acumulou.visibility = View.VISIBLE
            }

            val cor = Color.parseColor(resources.getStringArray(R.array.cores_primarias)[tipoJogo.value])

            val linearLayoutContainerRes = linearlayout_container_res
            val dezenasQueCabem = calcularDezenasQueCabem(linearLayoutContainerRes)
            val linhasNecessarias = dezenasSorteadas / dezenasQueCabem
            val dezenasPorLinha = dezenasSorteadas / linhasNecessarias

            if (jogo is MegaSena) {
                var dezenasAdicionadas = 0
                for (linear in 1..linhasNecessarias) {
                    val linearLayoutDezena = LayoutInflater.from(context).inflate(R.layout.linearlayout_dezenas, null)

                    if (linearLayoutDezena is LinearLayout) {
                        for (d in 1..dezenasPorLinha) {
                            val textViewDezena = LayoutInflater.from(context).inflate(R.layout.textview_dezena, null)
                            if (textViewDezena is TextView) {
                                textViewDezena.setBackgroundColor(cor)
                                textViewDezena.text = jogo.resultado[dezenasAdicionadas++].toString()
                                linearLayoutDezena.addView(textViewDezena)
                            }
                            if (dezenasAdicionadas == dezenasSorteadas) break
                        }
                    }
                    linearLayoutContainerRes.addView(linearLayoutDezena)
                }
            }
            activity?.let {
                val textViewNomeJogo = it.findViewById<TextView>(R.id.textView_nome_jogo)
                textViewNomeJogo.setTextColor(cor)
            }

            textView_concurso.text = concurso.toString()
            textView_data_sorteio.text = dataConcurso.toString()
            textView_ganhadores.text = ganhadores.toString()
        }
    }

    inner class DownloadTask: AsyncTask<Int, Unit, Jogo>() {

        override fun doInBackground(vararg numConcursos: Int?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled && numConcursos.isNotEmpty()) {
                val concurso = numConcursos[0]?: 1
                try {
                    result = when (tipoJogo) {
                        TipoDeJogo.MEGA_SENA -> MegaSena(numeroConcurso, resources)
                        TipoDeJogo.QUINA -> Quina(numeroConcurso, resources)
                        TipoDeJogo.LOTOFACIL -> Lotofacil(numeroConcurso, resources)
                        TipoDeJogo.LOTOMANIA -> Lotomania(numeroConcurso, resources)
                        TipoDeJogo.DUPLA_SENA -> DuplaSena(numeroConcurso, resources)
                        TipoDeJogo.FEDERAL -> Federal(numeroConcurso, resources)
                        TipoDeJogo.DIA_DE_SORTE -> DiaDeSorte(numeroConcurso, resources)
                        TipoDeJogo.TIMEMANIA -> Timemania(numeroConcurso, resources)
                        TipoDeJogo.LOTECA -> Loteca(numeroConcurso, resources)
                        TipoDeJogo.LOTOGOL -> Lotogol(numeroConcurso, resources)
                        else ->  null
                    }

                } catch (e: Exception) {
                    Log.d("ERRO", e.message)
                    e.message
                }
            }
            return result
        }

        override fun onPostExecute(result: Jogo?) {
            result?.let {
                atualizarDadosNaTela(it)
            }
        }
    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: TipoDeJogo): FragmentoDetalhesResultado {
            val fragmentoDetalhesResultado = FragmentoDetalhesResultado()
            fragmentoDetalhesResultado.arguments = Bundle().apply {
                putInt(NUMERO_CONCURSO, numConcurso)
                putInt(TIPO_JOGO, tipoJogo.value)
            }
            return fragmentoDetalhesResultado
        }
    }
}
