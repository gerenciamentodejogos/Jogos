package com.example.gerenciamentodejogos.apostas


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragmento_princ_aposta.*

class FragmentoPrincAposta : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_princ_aposta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    fun setUpListeners() {
        button_nova_aposta.setOnClickListener {
            acessarWeb()
        }
    }

    fun acessarWeb() {
        Log.d("GOOGLE - ", DadosWeb(2000).ObterResultados())
    }

}
