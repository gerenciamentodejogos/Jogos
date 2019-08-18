package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.modelos.Jogo
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import kotlinx.android.synthetic.main.fragmento_princ_prox_jogo.*

class FragmentoDetalhesResultado : Fragment() {
    private lateinit var resultadosViewModel: ResultadosViewModel
    private lateinit var dadosDoJogo: Jogo

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
        }
        if (numeroConcurso != PROXIMO_CONCURSO) {
            dadosDoJogo = DownloadTask().execute(numeroConcurso).get()
        } else {
            //CARREGAR INFORMAÇÕES DO PRÓXIMO CONCURSO
            textView_concurso.text = "PROXIMO CONCURSO"
            textView_data_sorteio.text = ""
            textView_ganhadores.text = ""

            textViewNum1.text = "    "
            textViewNum2.text = "    "
            textViewNum3.text = "    "
            textViewNum4.text = "    "
            textViewNum5.text = "    "
            textViewNum6.text = "    "
        }

    }

    private fun atualizarDadosNaTela(dadosJogo: Jogo) {
        with(dadosJogo) {
            textView_concurso.text = dadosResultado.concurso
            textView_data_sorteio.text = dadosResultado.dataStr
            textView_ganhadores.text = dadosResultado.ganhadores

            textViewNum1.text = dadosResultado.resultadoOrdenado.split('-')[0]
            textViewNum2.text = dadosResultado.resultadoOrdenado.split('-')[1]
            textViewNum3.text = dadosResultado.resultadoOrdenado.split('-')[2]
            textViewNum4.text = dadosResultado.resultadoOrdenado.split('-')[3]
            textViewNum5.text = dadosResultado.resultadoOrdenado.split('-')[4]
            textViewNum6.text = dadosResultado.resultadoOrdenado.split('-')[5]
        }

    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"

        fun newInstance(numConcurso: Int): FragmentoDetalhesResultado {
            val fragmentoDetalhesResultado = FragmentoDetalhesResultado()
            fragmentoDetalhesResultado.arguments = Bundle().apply {
                putInt(NUMERO_CONCURSO, numConcurso)
            }
            return fragmentoDetalhesResultado
        }
    }

    inner class DownloadTask: AsyncTask<Int, Unit, Jogo>() {
        override fun doInBackground(vararg numConcursos: Int?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled && numConcursos.isNotEmpty()) {
                val concurso = numConcursos[0]?: 1
                try {
                    result = Jogo(concurso)
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
}
