package com.example.gerenciamentodejogos.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.Usuario
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragmento_cadastro.*

class FragmentoCadastro : Fragment() {

    private lateinit var vmResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarListeners()
    }

    private fun validarFormulario(): Usuario? {
        val nome = editText_nome_cadastro.text.toString()
        val email = editText_email_cadastro.text.toString()
        val senha = editText_senha_cadastro.text.toString()
        val confirmaSenha = editText_senha_confirmacao.text.toString()

        return Usuario(nome, email, senha)
    }

    private fun configurarListeners() {
        button_cadastrar.setOnClickListener {
            val usuario = validarFormulario()
            if (usuario != null) {
                activity?.let {
                    val auth =  FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener(it) { tarefa ->
                        if (tarefa.isSuccessful) {
                            vmResultados.usuarioParaLogin.value = usuario
                            vmResultados.login()
                        } else {
                            Toast.makeText(context, "Erro ao fazer login!\nVerifique se o e-mail e senha digitados estão corretos!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } else {
                Toast.makeText(context, "Verifique se os campos estão corretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
