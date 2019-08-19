package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat.getColor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.modelos.Jogo
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_princ_prox_jogo.*
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

    private fun atualizarDadosNaTela(dadosJogo: Jogo) {
        with(dadosJogo) {
            if (dadosResultado.acumulado == "true") {
                textView_acumulou.visibility = View.VISIBLE
            }

//            textView_nome_jogo.setBackgroundColor(when (tipoJogo) {
//                0 -> R.color.cor_primaria_0
//                1 -> R.color.cor_primaria_1
//                2 -> R.color.cor_primaria_2
//                3 -> R.color.cor_primaria_3
//                4 -> R.color.cor_primaria_4
//                5 -> R.color.cor_primaria_5
//                6 -> R.color.cor_primaria_6
//                7 -> R.color.cor_primaria_7
//                8 -> R.color.cor_primaria_8
//                9 -> R.color.cor_primaria_9
//                else -> R.color.cor_primaria_0
//            })

            textViewNum1.text = dadosResultado.resultadoOrdenado.split('-')[0]
            textViewNum2.text = dadosResultado.resultadoOrdenado.split('-')[1]
            textViewNum3.text = dadosResultado.resultadoOrdenado.split('-')[2]
            textViewNum4.text = dadosResultado.resultadoOrdenado.split('-')[3]
            textViewNum5.text = dadosResultado.resultadoOrdenado.split('-')[4]
            textViewNum6.text = dadosResultado.resultadoOrdenado.split('-')[5]

            textView_concurso.text = dadosResultado.concurso
            textView_data_sorteio.text = dadosResultado.dataStr
            textView_ganhadores.text = dadosResultado.ganhadores
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
