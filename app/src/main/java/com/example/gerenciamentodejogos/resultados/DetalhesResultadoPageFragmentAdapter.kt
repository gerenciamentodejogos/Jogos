package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class DetalhesResultadoPageFragmentAdapter(private val proximoConcurso: Int, private val tipoJogo: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoDetalhesResultado.newInstance(position + 1, tipoJogo)
    }

    override fun getCount() = proximoConcurso
}