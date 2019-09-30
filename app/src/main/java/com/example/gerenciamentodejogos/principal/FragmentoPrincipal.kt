package com.example.gerenciamentodejogos.principal


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.ULTIMOS_CONCURSOS
import com.example.gerenciamentodejogos.resultados.FragmentoPrincipalPageAdapter
import kotlinx.android.synthetic.main.fragmento_principal.*

class FragmentoPrincipal : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarViewPager()
    }

    private fun configurarViewPager() {
        viewpager_principal.adapter = FragmentoPrincipalPageAdapter(ULTIMOS_CONCURSOS, definirOrdermJogos(), childFragmentManager)
        viewpager_principal.currentItem = 0
    }

    private fun definirOrdermJogos(): List<Int> {
        return listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }
}