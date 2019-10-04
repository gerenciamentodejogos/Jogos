package br.com.dev.jogosdaloteria.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.hardware.display.DisplayManagerCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.dados.MESES
import br.com.dev.jogosdaloteria.modelos.Resultado
import br.com.dev.jogosdaloteria.modelos.TipoDeJogo
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
import br.com.dev.jogosdaloteria.view_models.TelaPrincipalViewModel
import kotlinx.android.synthetic.main.fragmento_resultado_info_principais.*
import java.text.SimpleDateFormat

class FragmentoResultadoInfoPrincipais : Fragment() {

    private lateinit var vmResultados: ResultadosViewModel
    private lateinit var vmTelaPrincipal: TelaPrincipalViewModel

    private var tipoJogo: Int = 0
    private var numConcurso: Int = 0

    private var lineares: MutableList<LinearLayout> = mutableListOf()
    private var dezenas: MutableList<CardView> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            tipoJogo = it.getInt(TIPO_JOGO)
            numConcurso = it.getInt(NUMERO_CONCURSO)
        }

        activity?.let {
            vmResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]

            parentFragment?.let {
                vmTelaPrincipal = ViewModelProviders.of(it)[TelaPrincipalViewModel::class.java]
            }
        }

        return inflater.inflate(R.layout.fragmento_resultado_info_principais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tipoJogo != TipoDeJogo.LOTECA && tipoJogo != TipoDeJogo.LOTOGOL  && tipoJogo != TipoDeJogo.FEDERAL) {
            criarDezenas()
            adicionarDezenas()
        } else {
            configurarRecyclerView()
        }

        configurarObserverVM()
        atualizarTextConcurso()
    }

    private fun atualizarTextConcurso() {
        textView_concurso.setTextColor(vmResultados.getPropriedade(tipoJogo).corPrimaria)
        textView_concurso.text = numConcurso.toString()
    }

    private fun configurarObserverVM() {
        vmResultados.getResultado(tipoJogo, numConcurso)?.let {
            atualizarDadosPrincipais(it)
            return
        }

        activity?.let {
            vmResultados.jogos.observe(this, Observer {
                verificaJogo()
            })
        }
    }

    private fun verificaJogo() {
        vmResultados.getResultado(tipoJogo, numConcurso)?.let { jogo ->
            atualizarDadosPrincipais(jogo)
        }
    }

    private fun adicionarDezenas() {
        var itens = 2
        for (l in lineares) {
            linearlayout_container_res.addView(l, itens++ - 1)
        }
    }

    private fun criarDezenas() {
        var linear = layoutInflater.inflate(R.layout.linearlayout_dezenas, linearlayout_container_res, false) as LinearLayout
        lineares.add(linear)

        var textViewDezena: View
        val dezenasSorteadas = vmResultados.getPropriedade(tipoJogo).dezenasSorteadas

        var dezenasPorLinha: Int// = 0
        val linhasNecessarias: Double// = 0.0
        val quantasLinhas: Int// = 0

        textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear,false) as CardView
        dezenasPorLinha = calcularDezenasQueCabem(linear, textViewDezena)
        linhasNecessarias = dezenasSorteadas.toDouble().div(dezenasPorLinha)

        quantasLinhas = if (linhasNecessarias - linhasNecessarias.toInt() != 0.0) {
            (linhasNecessarias - (linhasNecessarias - linhasNecessarias.toInt())).toInt() + 1
        } else {
            linhasNecessarias.toInt()
        }

        val dezenasLinhas = dezenasSorteadas.toDouble().div(quantasLinhas)

        dezenasPorLinha = if (dezenasLinhas - dezenasLinhas.toInt() != 0.0) {
            dezenasLinhas.toInt() + 1
        } else {
            dezenasLinhas.toInt()
        }
        dezenas.add(textViewDezena)

        for (l in 2..quantasLinhas) {
            linear = layoutInflater.inflate(R.layout.linearlayout_dezenas, linearlayout_container_res, false) as LinearLayout
            lineares.add(linear)
        }

        for (d in 2..dezenasSorteadas) {
            textViewDezena = layoutInflater.inflate(R.layout.textview_dezena2, linear, false) as CardView
            dezenas.add(textViewDezena)
        }

        var dezenasAdicionadas = 0

        for (lin in lineares) {
            for (d in 1..dezenasPorLinha) {
                lin.addView(dezenas[dezenasAdicionadas++])
                if (dezenasAdicionadas == dezenasSorteadas) break
            }
        }
    }

    private fun calcularDezenasQueCabem(linear: View, cardView: View): Int {
        view?.let {
            val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)

            it.measure(measureSpec, measureSpec)
            linear.measure(measureSpec, measureSpec)
            cardView.measure(measureSpec, measureSpec)

            val metricas = DisplayMetrics()
            val pontos = Point()
            val c = Rect(0,0,0,0)
            context?.let {contexto ->
                val a = DisplayManagerCompat.getInstance(contexto)
                it.getHitRect(c)
                a.displays[0].getRealMetrics(metricas)
                a.displays[0].getRealSize(pontos)
            }

        }

        //TODO - OBTER TAMANHO DA TELA E DOS ELEMENTOS LINEARLAYOUT E TEXTVIEW DAS DEZENAS
        return 6
    }

    private fun configurarRecyclerView(){
        if (tipoJogo == TipoDeJogo.LOTECA || tipoJogo == TipoDeJogo.LOTOGOL) {
            recyclerview_resultado_deferencial.adapter = LotecaLotogolAdapter()
        } else if (tipoJogo == TipoDeJogo.FEDERAL) {
            recyclerview_resultado_deferencial.adapter = FederalAdapter()
        }
        recyclerview_resultado_deferencial.layoutManager = LinearLayoutManager(context)
    }

    private fun textoPlural(quant: Int, idRecurso: Int, textoInicial: String = "", textoFinal: String = ""): String {
        val plural = resources.getQuantityString(idRecurso, quant)
        return "${textoInicial}${quant} ${plural}${textoFinal}"
    }

    private fun exibirResultadoAdicional(jogo: Resultado) {
        textView_label_resultado_adicional.setTextColor(jogo.corPrimaria)
        textView_label_resultado_adicional.text = jogo.textoResultadoAdicinal

        textView_resultado_adicional.setTextColor(jogo.corPrimaria)

        textView_resultado_adicional.text = if (tipoJogo == TipoDeJogo.DIA_DE_SORTE) {
            MESES[jogo.resultadoAdicional[0].toInt()]
        } else {
            jogo.resultadoAdicional[0]
        }

        textView_label_resultado_adicional.visibility = View.VISIBLE
        textView_resultado_adicional.visibility = View.VISIBLE
    }

    private fun atualizarDadosPrincipais(jogo: Resultado) {
        if (tipoJogo == TipoDeJogo.LOTECA || tipoJogo == TipoDeJogo.LOTOGOL) {
            val lotecal_lotogol_adapter = recyclerview_resultado_deferencial.adapter
            if (lotecal_lotogol_adapter is LotecaLotogolAdapter) {
                lotecal_lotogol_adapter.alterarDados(jogo.resultadoLotogolLoteca)
            }
            recyclerview_resultado_deferencial.visibility = View.VISIBLE
        } else if (tipoJogo == TipoDeJogo.FEDERAL) {
            val federal_adapter = recyclerview_resultado_deferencial.adapter
            if (federal_adapter is FederalAdapter) {
                federal_adapter.alterarDados(jogo.resultadoFederal)
            }
            recyclerview_resultado_deferencial.visibility = View.VISIBLE
        } else {
            for (d in 0 until jogo.resultadoOrdenado.count()) {
                val text = dezenas[d].findViewById<TextView>(R.id.tv_dezena)
                text.text = jogo.resultadoOrdenado[d]
                text.setBackgroundColor(jogo.corPrimaria)
            }
            if (tipoJogo == TipoDeJogo.DIA_DE_SORTE || tipoJogo == TipoDeJogo.TIMEMANIA) {
                exibirResultadoAdicional(jogo)
            }
        }

        atualizarNumGanhadores(jogo)
        atualizarInfoSorteio(jogo)

        view?.let {
            it.visibility = View.VISIBLE
        }
        vmTelaPrincipal.dadosFragDetalhesCarregados.value = true
    }

    private fun atualizarInfoSorteio(jogo: Resultado) {
        textView_data_sorteio.text = formatarData(jogo.dataConcurso)
        if (jogo.tipoJogo == TipoDeJogo.FEDERAL || jogo.tipoJogo == TipoDeJogo.LOTECA || jogo.tipoJogo == TipoDeJogo.LOTOGOL) {
            textView_local_sorteio.text = getString(R.string.texto_sem_local_sorteio)
        } else {
            textView_local_sorteio.text = "${jogo.cidadeSorteio} - ${jogo.ufSorteio}\n${jogo.localSorteio}"
        }

        constraintLayout_info_sorteio.visibility = View.VISIBLE
    }

    private fun atualizarNumGanhadores(jogo: Resultado) {
        if (jogo.tipoJogo != TipoDeJogo.FEDERAL && jogo.tipoJogo != TipoDeJogo.DUPLA_SENA) {
            textView_ganhadores.setTextColor(jogo.corPrimaria)
            textView_ganhadores.text = if (jogo.acumulou) {
                getText(R.string.texto_acumulou)
            } else {
                textoPlural(jogo.premiacoes[0].numGanhadores, R.plurals.texto_ganhador, textoFinal = "!")
            }
            textView_ganhadores.visibility = View.VISIBLE
        }
    }

    private fun formatarData(valor: Long): String {
        return  SimpleDateFormat("dd/MMMM/yyyy").format(valor)
    }


    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: Int): FragmentoResultadoInfoPrincipais {
            val fragmentoPrincipalDetalhesResultado =
                FragmentoResultadoInfoPrincipais()
            fragmentoPrincipalDetalhesResultado.arguments = Bundle().apply {
                putInt(TIPO_JOGO, tipoJogo)
                putInt(NUMERO_CONCURSO, numConcurso)
            }
            return fragmentoPrincipalDetalhesResultado
        }
    }
}
