package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMOS_CONCURSOS
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultados : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var VMResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        configurarVMResultados()
        setUpPageView()
    }

    private fun configurarVMResultados() {
        activity?.let {
            VMResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
            VMResultados.jogoSelecionadoResultados.observe(it, Observer { tipoJogo ->
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
        val tipoJogo = VMResultados.tipoJogoAtual.value?: 0
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageAdapter(PROXIMOS_CONCURSOS[tipoJogo], tipoJogo, childFragmentManager)
        viewpage_detalhes_sorteio.currentItem = (PROXIMOS_CONCURSOS[tipoJogo] - 1) - 1
    }

    fun setUpListeners() {
        viewpage_detalhes_sorteio.addOnPageChangeListener (object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {}
        })

        textView_nome_jogo_resultados.setOnClickListener {
            context?.let {

                val builder = AlertDialog.Builder(it)
                builder.setTitle("Selecione um tipo de jogo")
                    .setItems(R.array.nomes_jogos, DialogInterface.OnClickListener { dialog, idJogo ->
                        VMResultados.jogoSelecionadoResultados.value = idJogo
                        VMResultados.tipoJogoAtual.value = idJogo
                    })
                builder.create()
                builder.show()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        VMResultados.tipoJogoAtual.value = p2
    }
}
