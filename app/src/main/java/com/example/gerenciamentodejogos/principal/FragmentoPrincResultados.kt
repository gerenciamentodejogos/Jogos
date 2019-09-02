package com.example.gerenciamentodejogos.principal


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.example.gerenciamentodejogos.R
import kotlinx.android.synthetic.main.fragmento_princ_resultados.*

class FragmentoPrincResultados : Fragment(), AdapterView.OnItemSelectedListener {
//    private lateinit var VMResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //configurarVMResultados()
        return inflater.inflate(R.layout.fragmento_princ_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarSpiner()
    }

    private fun configurarVMResultados() {
//        activity?.let {
//            VMResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
//        }
    }

    private fun configurarSpiner() {
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.nomes_jogos, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_jogo.adapter = adapter
                spinner_jogo.onItemSelectedListener = this
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        //VMResultados.tipoJogoAtual.value = p2
    }
}
