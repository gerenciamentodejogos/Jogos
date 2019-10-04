package br.com.dev.jogosdaloteria

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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

fun byteArrayToFoto(byteArray: ByteArray?): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray,0, byteArray?.size?:0)
}