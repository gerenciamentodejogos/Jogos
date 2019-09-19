package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.example.gerenciamentodejogos.principal.FragmentoPrincipalViewPage

class DetalhesResultadoPageAdapter(private val proximoConcurso: Int, private val tipoJogo: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoDetalhesResultado.newInstance(position + 1, tipoJogo)
    }

    override fun getCount() = proximoConcurso
}

class FragmentoPrincipalPageAdapter(private val ultimosConcursos: List<Int>, private val tiposJogos: List<Int>, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
//        return FragmentoResultadoInfoPrincipais.newInstance(ultimosConcursos[position], tiposJogos[position])
        return FragmentoPrincipalViewPage.newInstance(ultimosConcursos[position], tiposJogos[position])
    }

    override fun getCount() = tiposJogos.size
}
