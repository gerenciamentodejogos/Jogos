package br.com.dev.jogosdaloteria.persistencia

import android.content.Context
import br.com.dev.jogosdaloteria.modelos.Usuario

interface IPersistencia {
    val context: Context

    fun salvarResultado(tipoJogo: Int, numConcurso: Int, resultado: String): Boolean
    fun buscarResultado(tipoJogo: Int, numConcurso: Int): String?

    fun salvarPerfil(usuario: Usuario): Boolean
    fun buscarPerfil(email: String): Usuario?
}