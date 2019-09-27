package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.ULTIMOS_CONCURSOS
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultados : Fragment() {//}, AdapterView.OnItemSelectedListener {
    private lateinit var VMResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarVMResultados()
        setUpPageView()
        setUpListeners()
    }

    private fun configurarVMResultados() {
        activity?.let {
            VMResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
            VMResultados.tipoResultadoSelecionado.observe(it, Observer { tipoJogo ->
                if (tipoJogo != null) {
                    context?.let {
                        val dados = DadosDoJogo(tipoJogo, it.resources)
                        textView_nome_jogo_resultados.text = dados.nome
                        textView_nome_jogo_resultados.setTextColor(dados.corPrimaria)
                    }
                }
            })
        }
    }

    private fun setUpPageView() {
        val tipoJogo = VMResultados.tipoResultadoSelecionado.value?: 0
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageAdapter(ULTIMOS_CONCURSOS[tipoJogo], tipoJogo, childFragmentManager)
        viewpage_detalhes_sorteio.currentItem = (ULTIMOS_CONCURSOS[tipoJogo]) - 1
    }

    fun setUpListeners() {
        textView_nome_jogo_resultados.setOnClickListener {
            context?.let {

                val builder = AlertDialog.Builder(it)
                builder.setTitle("Selecione um tipo de jogo")
                    .setItems(R.array.nomes_jogos) { _, idJogo ->
                        VMResultados.irParaResultados(idJogo)
                    }
                builder.create()
                builder.show()
            }
        }
    }
}
