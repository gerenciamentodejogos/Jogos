package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
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
        configurarSpiner()
    }

    private fun configurarVMResultados() {
        activity?.let {
            VMResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
            VMResultados.tipoJogoAtual.observe(it, Observer {tipoJogo ->
                tipoJogo?.let {it ->
                    val dadosJogo = DadosDoJogo(tipoJogo, resources)

                    textView_nome_jogo.text = dadosJogo.nome
                    textView_nome_jogo.setTextColor(dadosJogo.corPrimaria)
                }
            })
        }
    }

    private fun configurarSpiner() {
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.nomes_jogos, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_jogo_res.adapter = adapter
                spinner_jogo_res.onItemSelectedListener = this
                spinner_jogo_res.setSelection(VMResultados.tipoJogoAtual.value?: 0)
            }
        }
    }

    private fun setUpPageView() {
        val tipoJogo = VMResultados.tipoJogoAtual.value?: 0
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageAdapter(PROXIMO_CONCURSO[tipoJogo], tipoJogo, childFragmentManager)
        viewpage_detalhes_sorteio.currentItem = (PROXIMO_CONCURSO[tipoJogo] - 1) - 1

    }

    fun setUpListeners() {
        viewpage_detalhes_sorteio.addOnPageChangeListener (object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {}
        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        VMResultados.tipoJogoAtual.value = p2
        //setUpPageView()
    }
}
