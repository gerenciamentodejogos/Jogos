package br.com.dev.jogosdaloteria

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo
import java.io.ByteArrayOutputStream

fun fotoToByteArray(foto: Drawable, formato: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, qualidade: Int = 100): ByteArray? {
    return if (foto is BitmapDrawable) {
        val bitmap = foto.bitmap
        val saida = ByteArrayOutputStream()
        bitmap.compress(formato, qualidade, saida)
        saida.toByteArray()
    } else {
        null
    }
}

fun byteArrayToFoto(byteArray: ByteArray?): Bitmap? {
    return if (byteArray != null) {
        BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
    } else {
        null
    }
}

fun listarTiposJogos(context: Context): List<DadosDoJogo> {
    val quantJogos = context.resources.getStringArray(R.array.nomes_jogos).size
    val lista = mutableListOf<DadosDoJogo>()

    for (j in 0 until quantJogos) {
        lista.add(DadosDoJogo(j, context))
    }

    return lista
}