package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.ColorStateList
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.gerenciamentodejogos.R

import com.example.gerenciamentodejogos.dados.PROXIMOS_CONCURSOS
import com.example.gerenciamentodejogos.modelos.*
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_detalhes_resultado.*
import java.text.NumberFormat
import java.text.SimpleDateFormat


class FragmentoDetalhesResultado : Fragment() {
    private lateinit var VMResultados: ResultadosViewModel

    private var lineares: MutableList<LinearLayout> = mutableListOf()
    private var dezenas: MutableList<CardView> = mutableListOf()

    private var tipoJogo: Int = 0
    private var numeroConcurso:Int = 1

    //private var tarefa: AsyncTask<Unit, Unit, Jogo> = DownloadTask()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            numeroConcurso = it.getInt(NUMERO_CONCURSO)
            tipoJogo = it.getInt(TIPO_JOGO)
        }

        activity?.let {
            VMResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
        }

        tipoJogo = VMResultados.tipoJogoAtual.value?: 0

        carregarFragmentos()

        return inflater.inflate(R.layout.fragmento_detalhes_resultado, container, false)
    }

    private fun carregarFragmentos() {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_frag_detalhes_principais, FragmentoResultadoInfoPrincipais.newInstance(numeroConcurso, tipoJogo))
        fragmentTransaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
        configurarVMResultados()

        progressBar_detalhes_res.indeterminateTintList = ColorStateList.valueOf(DadosDoJogo(tipoJogo, resources).corPrimaria)
        textView_label_progresso.text = "${getString(R.string.texto_carregando_dados_concurso)} ${numeroConcurso}"
    }

    private fun configurarVMResultados() {
        activity?.let {
            val jogo = VMResultados.getResultado(tipoJogo, numeroConcurso, resources)
            if (jogo == null) {
                VMResultados.jogos.observe(this, Observer {
                    verificaJogo()
                })

            } else {
                atualizarDadosNaTela(jogo)
            }
        }
    }

    private fun verificaJogo() {
        VMResultados.getResultado(tipoJogo, numeroConcurso)?.let { jogo ->
            atualizarDadosNaTela(jogo)
        }
    }

    private fun configurarRecyclerView(){
        recyclerview_premiacoes.adapter = PremiacoesAdapter()
        recyclerview_premiacoes.layoutManager = LinearLayoutManager(context)

        recyclerview_ganhadores.adapter = GanhadoresAdapter()
        recyclerview_ganhadores.layoutManager = LinearLayoutManager(context)

        recyclerview_valores.adapter = ValoresAdapter()
        recyclerview_valores.layoutManager = LinearLayoutManager(context)
    }

    private fun atualizarDadosNaTela(jogo: Jogo) {
        //TODO -  RETIRAR '&& jogo.concurso < PROXIMOS_CONCURSOS[tipoJogo]'
        if (jogo.encerrado && jogo.concurso < PROXIMOS_CONCURSOS[tipoJogo]) {
                textview_total_premiacoes.setTextColor(jogo.corPrimaria)
                textView_label_total_premiacoes.setTextColor(jogo.corPrimaria)
                // ######################################
                val premiacoes_adapter = recyclerview_premiacoes.adapter
                if (premiacoes_adapter is PremiacoesAdapter) {
                    premiacoes_adapter.alterarDados(jogo.premiacoes)
                }
                textview_total_premiacoes.text = NumberFormat.getCurrencyInstance().format(jogo.valorTotalPremiacoes)
                cardView_ganhadores.visibility = View.VISIBLE

                if (jogo.premiacoes[0].numGanhadores != 0) {
                    val ganhadores_adapter = recyclerview_ganhadores.adapter
                    if (ganhadores_adapter is GanhadoresAdapter) {
                        ganhadores_adapter.alterarDados(jogo.ganhadores)
                    }
                    cardView_ganhadores.visibility = View.VISIBLE
                } else {
                    cardView_ganhadores.visibility = View.GONE
                }
                cardView_premiacoes.visibility = View.VISIBLE

                // ############ VALORES #################
                val valores_adapter = recyclerview_valores.adapter
                if (valores_adapter is ValoresAdapter) {
                    valores_adapter.cor = jogo.corPrimaria
                    valores_adapter.alterarDados(jogo.ListaValoresTotais())
                }
                cardView_valores.visibility = View.VISIBLE
                // ######################################

                textView_data_sorteio.text = formatarData(jogo.dataConcurso)
                textView_local_sorteio.text = "${jogo.cidadeSorteio} - ${jogo.ufSorteio}\n${jogo.localSorteio}"

                constraintLayout_info_sorteio.visibility = View.VISIBLE
        } else {
            //CARREGAR INFORMAÇÕES DO PRÓXIMO CONCURSO
            textView_data_sorteio.text = ""
        }

        linear_progresso.visibility = View.GONE
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
