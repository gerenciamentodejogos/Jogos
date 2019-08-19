package com.example.gerenciamentodejogos.principal


import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.fragmento_princ_aposta.*
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.*
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection


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

        }
    }
}
