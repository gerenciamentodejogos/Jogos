package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.gerenciamentodejogos.dados.PAGINA_ATUAL
import com.example.gerenciamentodejogos.dados.PAGINA_CARREGADA

class DetalhesResultadoPageFragmentAdapter(private val proximoConcurso: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoDetalhesResultado.newInstance(position + 1)
    }

    override fun getCount() = proximoConcurso
}