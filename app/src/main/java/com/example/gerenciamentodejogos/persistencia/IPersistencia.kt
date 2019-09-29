package com.example.gerenciamentodejogos.persistencia

import android.content.Context

interface IPersistencia {

    val context: Context

    fun salvarResultado(tipoJogo: Int, numConcurso: Int, resultado: String): Boolean
    fun buscarResultado(tipoJogo: Int, numConcurso: Int): String?
}