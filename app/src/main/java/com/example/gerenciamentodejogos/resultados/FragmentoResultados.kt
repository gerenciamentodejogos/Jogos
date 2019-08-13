package com.example.gerenciamentodejogos.resultados


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PAGINA_ATUAL
import com.example.gerenciamentodejogos.dados.resultado_concurso
import kotlinx.android.synthetic.main.fragmento_resultados.*
import java.time.LocalDate

class FragmentoResultados : Fragment() {

    var PROXIMO_CONCURSO = 9

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()

        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageFragmentAdapter(PROXIMO_CONCURSO, childFragmentManager)

        subscribe()
    }

    fun subscribe() {
        viewpage_detalhes_sorteio.currentItem = 3
    }

    fun setUpListeners() {

        button_concurso.setOnClickListener {

        }

        viewpage_detalhes_sorteio.addOnPageChangeListener (object: ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {
                resultado_concurso = position + 1
                PAGINA_ATUAL = position + 1
                if (position + 1 == viewpage_detalhes_sorteio.adapter?.count) {
                    button_concurso.text = "ESTE É O PRÓXIMO"
                } else {
                    button_concurso.text = (position + 1).toString()
                }
            }
        })
    }
}
