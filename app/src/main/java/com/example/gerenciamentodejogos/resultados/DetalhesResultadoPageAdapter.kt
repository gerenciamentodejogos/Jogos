package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.gerenciamentodejogos.principal.FragmentoPrincipalViewPage

class DetalhesResultadoPageAdapter(private val ultimoConcurso: Int, private val tipoJogo: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == ultimoConcurso) {
            FragmentoProximoConcurso.newInstance(tipoJogo)
        } else {
            FragmentoDetalhesResultado.newInstance(position + 1, tipoJogo)
        }
    }

    override fun getCount() = ultimoConcurso + 1
}

class FragmentoPrincipalPageAdapter(private val ultimosConcursos: List<Int>, private val tiposJogos: List<Int>, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoPrincipalViewPage.newInstance(ultimosConcursos[position], tiposJogos[position])
    }

    override fun getCount() = tiposJogos.size
}
