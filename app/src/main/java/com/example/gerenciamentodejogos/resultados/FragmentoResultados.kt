package com.example.gerenciamentodejogos.resultados


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.modelos.TipoDeJogo
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultados : Fragment() {

    private var tipoJogo: TipoDeJogo = TipoDeJogo.MEGA_SENA

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            tipoJogo = when (it.getInt(TIPO_JOGO)) {
                0 -> TipoDeJogo.MEGA_SENA
                1 -> TipoDeJogo.QUINA
                2 -> TipoDeJogo.LOTOFACIL
                3 -> TipoDeJogo.LOTOMANIA
                4 -> TipoDeJogo.DUPLA_SENA
                5 -> TipoDeJogo.FEDERAL
                6 -> TipoDeJogo.DIA_DE_SORTE
                7 -> TipoDeJogo.TIMEMANIA
                8 -> TipoDeJogo.LOTECA
                9 -> TipoDeJogo.LOTOGOL

                else ->  TipoDeJogo.MEGA_SENA
            }
        }

        setUpListeners()
        setUpPageView()
    }

    private fun setUpPageView() {
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageFragmentAdapter(PROXIMO_CONCURSO[tipoJogo.value], tipoJogo, childFragmentManager)
        viewpage_detalhes_sorteio.offscreenPageLimit = 1

        viewpage_detalhes_sorteio.currentItem = (PROXIMO_CONCURSO[tipoJogo.value] - 1) - 1
    }

    fun setUpListeners() {
        viewpage_detalhes_sorteio.addOnPageChangeListener (object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {}
        })
    }

    companion object {
        val TIPO_JOGO = "tipo_jogo"
    }
}
