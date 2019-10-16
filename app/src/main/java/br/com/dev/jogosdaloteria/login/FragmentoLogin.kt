package br.com.dev.jogosdaloteria.login


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
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

    private fun validarFormulario(): ResultadosViewModel.DadosLogin? {
        val email = editText_email_login.text.toString()
        val senha = editText_senha_login.text.toString()

        return ResultadosViewModel.DadosLogin(email, senha)
    }

    private fun configurarListeners() {
        button_login.setOnClickListener {
            val usuario = validarFormulario()
            if (usuario != null) {
                frame_progresso_login.visibility = View.VISIBLE
                vmResultados.dadosLogin.value = ResultadosViewModel.DadosLogin(usuario.email, usuario.senha)
            } else {
                Toast.makeText(context, "Verifique se o e-mail e senha digitados estão corretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
