package com.example.gerenciamentodejogos.login


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Usuario
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_login.*

class FragmentoLogin : Fragment() {

    private lateinit var vmResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            vmResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
        }

        configurarListeners()
    }

    private fun validarFormulario(): Usuario? {
        val email = editText_email_login.text.toString()
        val senha = editText_senha_login.text.toString()

        return Usuario("", email, senha)
    }

    private fun configurarListeners() {
        button_login.setOnClickListener {
            val usuario = validarFormulario()
            if (usuario != null) {
                vmResultados.usuarioParaLogin.value = usuario
                vmResultados.login()
            } else {
                Toast.makeText(context, "Verifique se o e-mail e senha digitados estão corretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
