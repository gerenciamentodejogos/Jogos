package com.example.gerenciamentodejogos.resultados

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.TipoDeJogo
import kotlinx.android.synthetic.main.fragmento_princ_prox_jogo.*

class DetalhesResultadoPageFragmentAdapter(private val proximoConcurso: Int, private val tipoJogo: Int, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FragmentoDetalhesResultado.newInstance(position + 1, tipoJogo)
    }

    override fun getCount() = proximoConcurso
}
