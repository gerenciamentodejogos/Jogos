package com.example.gerenciamentodejogos.resultados


import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PAGINA_CARREGADA
import com.example.gerenciamentodejogos.dados.resultado_concurso
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*

class FragmentoDetalhesResultado : Fragment() {

    private lateinit var resultadosViewModel: ResultadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let {
            resultadosViewModel = ViewModelProviders.of(it)
                .get(ResultadosViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    fun subscribe() {
        textView_ganhadores.text = PAGINA_CARREGADA.toString()
    }

    companion object FragmentoDetalhesResultado {
        var concurso: Int = 0

        fun newInstance(numConcurso: Int): Fragment {
            concurso = numConcurso
            return FragmentoDetalhesResultado()
        }
    }
}
