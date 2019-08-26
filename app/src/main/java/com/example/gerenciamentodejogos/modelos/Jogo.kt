package com.example.gerenciamentodejogos.modelos

import android.content.res.Resources
import android.graphics.Color
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import org.json.JSONObject
import java.util.*


//class Jogos(val numConcurso: Int, val tipoJogo: Int = 0, val url: String, val parametro1: String, val parametro2: String) {
//    val dadosResultado = ObterDados()
//    val dezenasSorteadas = dadosResultado.resultado.count()
//
//    private fun ObterDados(): DadosResultado {
//
//        val TEM_NO_BANCO_DE_DADOS = false
//
//        return if (TEM_NO_BANCO_DE_DADOS) {
//            //Obter os dados localmente
//            DadosResultado("")
//        } else {
//            //var url_caixa = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/=/"
//            val url_caixa = url + parametro1 + Date().time + parametro2 + numConcurso
//
//            val dadosJSON = DadosWeb(url_caixa).ObterResultados()
//            DadosResultado(dadosJSON)
//        }
//    }
//}
//
//class DadosResultado(private var stringJSON: String) {
//    private var dadosJSON = JSONObject()
//
//    init {
//        stringJSON = stringJSON.substringAfter("<body>", stringJSON)
//        stringJSON = stringJSON.substringBefore("</body>", stringJSON).trim()
//
//        dadosJSON = JSONObject(stringJSON)
//    }
//
//    val concurso: Int = dadosJSON.get("concurso").toString().toInt()
//    val data: String = dadosJSON.get("data").toString()
//    val resultado: List<Int> = listarDezenas(dadosJSON.get("resultado").toString())
//    val ganhadores: Int = dadosJSON.get("ganhadores").toString().toInt()
//    val acumulado: Boolean = dadosJSON.get("acumulado").toString().toBoolean()
//
//    private fun listarDezenas(dados: String, separador: Char = '-'): List<Int> {
//        var lista = mutableListOf<Int>()
//        for (d in dados.split(separador)){
//            lista.add(d.toInt())
//        }
//        return lista
//    }
//}

enum class TipoDeJogo(val value: Int){
    MEGA_SENA(0),
    QUINA(1),
    LOTOFACIL(2),
    LOTOMANIA(3),
    DUPLA_SENA(4),
    FEDERAL(5),
    DIA_DE_SORTE(6),
    TIMEMANIA(7),
    LOTECA(8),
    LOTOGOL(9)
}

open class DadosDoJogo(val tipoJogo: TipoDeJogo, val recursos: Resources) {
    val nome: String = recursos.getStringArray(R.array.nomes_jogos)[tipoJogo.value]
    val nomeEspecial: String = recursos.getStringArray(R.array.nomes_especiais)[tipoJogo.value]
    val url_resultado: String = recursos.getStringArray(R.array.urls_resultados)[tipoJogo.value]
    val corPrimaria:Int = Color.parseColor(recursos.getStringArray(R.array.cores_primarias)[tipoJogo.value])
    val corSecundaria:Int = Color.parseColor(recursos.getStringArray(R.array.cores_primarias)[tipoJogo.value])

    val dezenasSorteadas: Int = recursos.getIntArray(R.array.dezenas_sorteadas)[tipoJogo.value]
    val dezenasMinima: Int = recursos.getIntArray(R.array.dezenas_minima)[tipoJogo.value]
    val dezenasMaxima: Int = recursos.getIntArray(R.array.dezenas_maxima)[tipoJogo.value]
    val dezenasTotal: Int = recursos.getIntArray(R.array.dezenas_total)[tipoJogo.value]

    val acertos: List<Int>
        get() {
            val retorno = mutableListOf<Int>()
            val lista_acertos = recursos.getStringArray(R.array.acertos)[tipoJogo.value]
            for (a in lista_acertos) {
                retorno.add(a.toInt())
            }
            return retorno
        }

}

open class Jogo(val concurso: Int, tipoJogo: TipoDeJogo, recursos: Resources): DadosDoJogo(tipoJogo, recursos) {
    data class InfoGanhadores(val uf: String, val cidade: String, val quantidade: Int, val meioEletronico: Boolean)

    protected var dadosJSON: JSONObject = JSONObject()

    init {
        var stringJSON = buscalDadosLocalmente()
        if (stringJSON.isEmpty()) {
            stringJSON = buscarDadosWeb()
            salvarDadosLocalmente(stringJSON)
        }

        dadosJSON = JSONObject(stringJSON)
    }

    val dataConcurso: Int = dadosJSON[""].toString().toInt()
    val dataProximoConcurso: Int = dadosJSON[""].toString().toInt()
    val ganhadores: Int = dadosJSON[""].toString().toInt()
    val valorPremioPrincipal: Double = dadosJSON[""].toString().toDouble()
    val valorTotalPremioPrincipal: Double = dadosJSON[""].toString().toDouble()
    val acumulou: Boolean = dadosJSON[""].toString().toBoolean()
    val localSorteio: String = dadosJSON[""].toString()
    val cidadeSorteio: String = dadosJSON[""].toString()
    val ufSorteio: String = dadosJSON[""].toString()
    val valorArrecadado: Double = dadosJSON[""].toString().toDouble()
    val observacao: String = dadosJSON[""].toString()
    val erro: String = dadosJSON[""].toString()
    val valorAcumuladoProxConcurso: Double = dadosJSON[""].toString().toDouble()
    val estimativaProxConcurso: Double = dadosJSON[""].toString().toDouble()

    //    TODO - DEFINIR COMO SERÁ O RETORNO DE 'encerrado'
    val encerrado: Boolean = false


//    TODO - OBTER LISTA DE MENSAGENS E INFORMAÇÕES DOS GANHADORES
//    val mensagens: List<String> = listOf()
//    val infoGanhadores: List<InfoGanhadores> = listOf()

    private fun salvarDadosLocalmente(stringJSON: String) {
        //TODO - SALVAR DADOS NO BANCO LOCAL
    }

    private fun buscalDadosLocalmente(): String {
        // TODO - BUSCAR DADOS DO BANCO LOCAL
        return ""
    }

    private fun buscarDadosWeb(): String {
        var stringJSON = DadosWeb(obterURLResultado()).ObterResultados()
        stringJSON = stringJSON.substringAfter("<body>", stringJSON)
        stringJSON = stringJSON.substringBefore("</body>", stringJSON).trim()

        return stringJSON
    }

    protected fun listarResultado(dados: String, separador: Char = '-'): List<String> {
        var lista = mutableListOf<String>()
        for (d in dados.split(separador)){
            lista.add(d)
        }
        return lista
    }

    protected fun obterURLResultado(): String {
        var url_temp = url_resultado
        url_temp += recursos.getString(R.string.url_parametro_1) + Date().time
        url_temp += recursos.getString(R.string.url_parametro_2) + concurso.toString()
        return url_temp
    }
}


class MegaSena(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.MEGA_SENA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Quina(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.QUINA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Lotofacil(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.LOTOFACIL, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Lotomania(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.LOTOMANIA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class DuplaSena(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.DUPLA_SENA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Federal(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.FEDERAL, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class DiaDeSorte(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.DIA_DE_SORTE, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Timemania(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.TIMEMANIA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Loteca(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.LOTECA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}

class Lotogol(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo.LOTOGOL, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON["resultado"].toString())
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON["resultadoOrdenado"].toString())
    val ganhadoresQuina: Int = dadosJSON["ganhadoresQuina"].toString().toInt()
    val ganhadoresQuadra: Int = dadosJSON["ganhadoresQuadra"].toString().toInt()
    val valorQuina: Double = dadosJSON["valorQuina"].toString().toDouble()
    val valorTotalQuina: Double = dadosJSON["valorTotalQuina"].toString().toDouble()
    val valorQuadra : Double = dadosJSON["valorQuadra"].toString().toDouble()
    val valorTotalQuadra: Double = dadosJSON["valorTotalQuadra"].toString().toDouble()
    val concursoEspecial: Boolean = dadosJSON["concursoEspecial"].toString().toBoolean()
    val acumuladoEapecial: Double = dadosJSON["acumuladoEapecial"].toString().toDouble()
}
