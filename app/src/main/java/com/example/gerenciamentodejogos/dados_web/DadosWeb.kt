package com.example.gerenciamentodejogos.dados_web

import org.jsoup.Jsoup

class DadosWeb(val numConcurso: Int, val tipoJogo: Int = 0) {

    fun ObterResultados(): String {
        val doc = Jsoup.connect("https://www.google.com")
        val doc2 = doc.get()
        return doc2.outerHtml()
    }
}