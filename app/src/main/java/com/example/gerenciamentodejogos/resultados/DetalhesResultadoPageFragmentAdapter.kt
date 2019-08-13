package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.gerenciamentodejogos.dados.PAGINA_ATUAL
import com.example.gerenciamentodejogos.dados.PAGINA_CARREGADA

class DetalhesResultadoPageFragmentAdapter(val proximoConcurso: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        PAGINA_CARREGADA = if (PAGINA_ATUAL == position) PAGINA_ATUAL else position + 1

        //return FragmentoDetalhesResultado.newInstance(position + 1)
        return FragmentoDetalhesResultado()
    }

    override fun getCount() = proximoConcurso
}