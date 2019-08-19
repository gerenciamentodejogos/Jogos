package com.example.gerenciamentodejogos.principal


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import kotlinx.android.synthetic.main.fragmento_princ_aposta.*

class FragmentoPrincipal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_principal, container, false)
    }
}
