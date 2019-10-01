package br.com.dev.jogosdaloteria.login


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Usuario
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragmento_cadastro.*
import kotlinx.android.synthetic.main.fragmento_login.*
import kotlinx.android.synthetic.main.fragmento_perfil.*

class FragmentoPerfil : Fragment() {

    private lateinit var vmResultados: ResultadosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            vmResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
        }

        configurarListeners()
        atualizarInterface()
    }

    private fun validarFormulario(): Usuario? {
        val email = textView_email_perfil.text.toString()
        val nome = editText_nome_perfil.text.toString()
        val senha = editText_senha_perfil.text.toString()
        val novaSenha = editText_nova_senha.text.toString()
        val senhaConfirmacao = editText_nova_senha_confirma.text.toString()
        val foto = ""

        return Usuario(nome, email, novaSenha, foto)
    }

    private fun configurarListeners() {
        button_salvar_perfil.setOnClickListener {

        }
    }

    private fun atualizarInterface() {
        vmResultados.usuario.value?.let {
            imageView_foto_perfil.setImageResource(R.drawable.ic_avatar)
            editText_nome_perfil.setText(it.nome)
            textView_email_perfil.text = it.email
        }
    }
}
