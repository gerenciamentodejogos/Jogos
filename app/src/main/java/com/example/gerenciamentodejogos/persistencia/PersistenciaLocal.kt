package com.example.gerenciamentodejogos.persistencia

import android.content.Context

class PersistenciaLocal(override val context: Context) : IPersistencia {

    override fun salvarResultado(tipoJogo: Int, numConcurso: Int, resultado: String): Boolean {
        return false
    }

    override fun buscarResultado(tipoJogo: Int, numConcurso: Int): String? {
        return null
    }
}