package br.com.dev.jogosdaloteria.principal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.dados.ULTIMOS_CONCURSOS
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo
import br.com.dev.jogosdaloteria.modelos.TipoDeJogo
import br.com.dev.jogosdaloteria.resultados.FragmentoPrincipalPageAdapter
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

        tabLayout_principal.setupWithViewPager(viewpager_principal, true)
        context?.let {
            tabLayout_principal.setSelectedTabIndicatorColor(DadosDoJogo(TipoDeJogo.MEGA_SENA, it).corPrimaria)
        }

        viewpager_principal.adapter = FragmentoPrincipalPageAdapter(ULTIMOS_CONCURSOS, definirOrdermJogos(), childFragmentManager)
        viewpager_principal.currentItem = 0

        viewpager_principal.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                context?.let {
                    tabLayout_principal.setSelectedTabIndicatorColor(DadosDoJogo(position, it).corPrimaria)
                }
            }
        })
    }

    private fun definirOrdermJogos(): List<Int> {
        return listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }
}