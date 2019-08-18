package com.example.gerenciamentodejogos.dados_web

import org.jsoup.Jsoup

class DadosWeb(private val url_caixa: String) {

    fun ObterResultados(): String {
        val doc = Jsoup.connect(url_caixa)
        val doc2 = doc.get()
        return doc2.outerHtml()
    }
}