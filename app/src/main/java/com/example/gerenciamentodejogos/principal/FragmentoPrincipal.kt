package com.example.gerenciamentodejogos.principal


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados.PROXIMOS_CONCURSOS
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
        viewpager_principal.adapter = FragmentoPrincipalPageAdapter(PROXIMOS_CONCURSOS, definirOrdermJogos(), childFragmentManager)
        viewpager_principal.currentItem = 0
    }

    private fun definirOrdermJogos(): List<Int> {
        return listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

//    protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        var heightMeasureSpec = heightMeasureSpec
//
//        var height = 0
//        for (i in 0 until getChildCount()) {
//            val child = getChildAt(i)
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
//            val h = child.getMeasuredHeight()
//            if (h > height) height = h
//        }
//
//        if (height != 0) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
//        }
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }
}
