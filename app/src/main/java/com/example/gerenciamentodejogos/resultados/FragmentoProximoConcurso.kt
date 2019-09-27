package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Jogo
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_proximo_concurso.*
import kotlinx.android.synthetic.main.fragmento_resultado_info_principais.*
import java.text.NumberFormat
import java.text.SimpleDateFormat

class FragmentoProximoConcurso : Fragment() {
    private lateinit var VMResultados: ResultadosViewModel

    private var tipoJogo: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            tipoJogo = it.getInt(TIPO_JOGO)
        }

        return inflater.inflate(R.layout.fragmento_proximo_concurso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarVM()
    }

    private fun configurarVM() {
        activity?.let {
            VMResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
            VMResultados.ultimoConcurso.value?.let {
                val ultimoConcurso = it[tipoJogo]
                VMResultados.getResultado(tipoJogo, ultimoConcurso)?.let {
                    atualizarDados(it)
                    return
                }

                VMResultados.jogos.observe(this, Observer {
                    verificaJogo(ultimoConcurso)
                })
            }
       }
    }

    private fun verificaJogo(numConcurso: Int) {
        VMResultados.getResultado(tipoJogo, numConcurso)?.let { jogo ->
            atualizarDados(jogo)
            VMResultados.jogos.removeObservers(this)
        }
    }

    private fun atualizarDados(jogo: Jogo) {
        textView_proximo_concurso.setTextColor(jogo.corPrimaria)
        textView_valor_estimativa.setTextColor(jogo.corPrimaria)

        textView_proximo_concurso.text = jogo.concurso.plus(1).toString()
        if (jogo.acumulou) {
            textView_concurso_acumulado.setTextColor(jogo.corPrimaria)
            textView_concurso_acumulado.visibility = View.VISIBLE
        }
        textView_valor_estimativa.text = NumberFormat.getCurrencyInstance().format(jogo.estimativaProxConcurso)
        textView_data_proximo_sorteio.text = formatarData(jogo.dataProximoConcurso)

        cardView_proximo_concurso.visibility = View.VISIBLE
    }

    private fun formatarData(valor: Long): String {
        return  SimpleDateFormat("dd/MM/yyyy").format(valor)
    }


    companion object {
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(tipoJogo: Int): FragmentoProximoConcurso {
            val fragmentoProximoConcurso = FragmentoProximoConcurso()
            fragmentoProximoConcurso.arguments = Bundle().apply {
                putInt(TIPO_JOGO, tipoJogo)
            }
            return fragmentoProximoConcurso
        }
    }

}
