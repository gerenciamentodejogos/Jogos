package br.com.dev.jogosdaloteria.login


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.modelos.Usuario
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_perfil.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import br.com.dev.jogosdaloteria.byteArrayToFoto
import br.com.dev.jogosdaloteria.fotoToByteArray
import java.io.ByteArrayOutputStream


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

        val bitmap = (imageView_foto_perfil.drawable as BitmapDrawable).bitmap
        val saida = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, saida)
        val foto = fotoToByteArray(imageView_foto_perfil.drawable)

        return Usuario(nome, email, foto)
    }

    private fun configurarListeners() {
        button_salvar_perfil.setOnClickListener {
            val usuario = validarFormulario()
            if (usuario != null) {
                vmResultados.atualizarPerfil(usuario)
            } else {
                Toast.makeText(context, "Verifique se os dados estão corretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarInterface() {
        vmResultados.usuario.value?.let {
            imageView_foto_perfil.setImageBitmap(byteArrayToFoto(it.foto))
            editText_nome_perfil.setText(it.nome)
            textView_email_perfil.text = it.email
        }
    }
}
