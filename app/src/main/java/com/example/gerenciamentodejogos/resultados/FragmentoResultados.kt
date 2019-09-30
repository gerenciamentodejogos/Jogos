package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.TELA_RESULTADOS
import com.example.gerenciamentodejogos.dados.ULTIMOS_CONCURSOS
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.modelos.TipoDeJogo
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultados : Fragment() {//}, AdapterView.OnItemSelectedListener {
    private lateinit var vmResultados: ResultadosViewModel
    private var tipoJogo = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarVMResultados()
        setUpPageView()
        setUpListeners()
        atualizarInterface()
    }

    private fun atualizarInterface() {
        textView_nome_jogo_resultados.text = vmResultados.getPropriedade(tipoJogo).nome
        textView_nome_jogo_resultados.setTextColor(vmResultados.getPropriedade(tipoJogo).corPrimaria)
    }

    private fun configurarVMResultados() {
        activity?.let {
            vmResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
            tipoJogo = vmResultados.tipoResultadoSelecionado.value?: TipoDeJogo.MEGA_SENA
        }
    }

    private fun setUpPageView() {
        val tipoJogo = vmResultados.tipoResultadoSelecionado.value?: 0
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageAdapter(ULTIMOS_CONCURSOS[tipoJogo], tipoJogo, childFragmentManager)
        viewpage_detalhes_sorteio.currentItem = (ULTIMOS_CONCURSOS[tipoJogo]) - 1
    }

    fun setUpListeners() {
        textView_nome_jogo_resultados.setOnClickListener {
            context?.let {

                val builder = AlertDialog.Builder(it)
                builder.setTitle("Selecione um tipo de jogo")
                    .setItems(R.array.nomes_jogos) { _, idJogo ->
//                        vmResultados.irParaResultados(idJogo)
                        vmResultados.irParaTela(TELA_RESULTADOS, idJogo)
                    }
                builder.create()
                builder.show()
            }
        }
    }
}
