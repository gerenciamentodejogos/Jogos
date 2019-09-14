package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class DetalhesResultadoPageAdapter(private val proximoConcurso: Int, private val tipoJogo: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoDetalhesResultado.newInstance(position + 1, tipoJogo)
    }

    override fun getCount() = proximoConcurso
}

class ResultadoPrincipalPageAdapter(private val ultimosConcursos: List<Int>, private val tiposJogos: List<Int>, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    var fragmentos: MutableList<Fragment> = mutableListOf()

    private fun gerarListaFragmentos() {
        ultimosConcursos.forEachIndexed { index, concurso ->
            fragmentos.add(FragmentoResultadoInfoPrincipais.newInstance(concurso, tiposJogos[index]))
        }
    }

    init {
        gerarListaFragmentos()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentos[position]
    }

    override fun getCount() = fragmentos.size
}
