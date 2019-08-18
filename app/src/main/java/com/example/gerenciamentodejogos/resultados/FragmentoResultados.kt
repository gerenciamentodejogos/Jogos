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
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import kotlinx.android.synthetic.main.fragmento_resultados.*
import java.time.LocalDate

class FragmentoResultados : Fragment() {

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
        viewpage_detalhes_sorteio.offscreenPageLimit = 1

        subscribe()
    }

    fun subscribe() {
        viewpage_detalhes_sorteio.currentItem = (PROXIMO_CONCURSO - 1) - 1
    }

    fun setUpListeners() {

        viewpage_detalhes_sorteio.addOnPageChangeListener (object: ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {}
        })
    }
}
