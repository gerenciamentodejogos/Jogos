package com.example.gerenciamentodejogos.modelos

import android.content.res.Resources
import android.content.res.Resources.getSystem
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import org.json.JSONObject
import java.util.*


class Jogo(val numConcurso: Int, val tipoJogo: Int = 0, val url: String, val parametro1: String, val parametro2: String) {
    val dadosResultado = ObterDados()
    val dezenasSorteadas = dadosResultado.resultado.count()

    private fun ObterDados(): DadosResultado {

        val TEM_NO_BANCO_DE_DADOS = false

        return if (TEM_NO_BANCO_DE_DADOS) {
            //Obter os dados localmente
            DadosResultado("")
        } else {
            //var url_caixa = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/=/"
            val url_caixa = url + parametro1 + Date().time + parametro2 + numConcurso

            val dadosJSON = DadosWeb(url_caixa).ObterResultados()
            DadosResultado(dadosJSON)
        }
    }

    private fun salvarDadosLocalmente() {

    }

}

class DadosResultado(private var stringJSON: String) {
    //TODO - AJUSTAR ISSO TUDO

    private var dadosJSON = JSONObject()

    init {
        stringJSON = stringJSON.substringAfter("<body>", stringJSON)
        stringJSON = stringJSON.substringBefore("</body>", stringJSON).trim()

        dadosJSON = JSONObject(stringJSON)
    }

    val concurso: Int = dadosJSON.get("concurso").toString().toInt()
    val data: String = dadosJSON.get("data").toString()

    val resultado: List<Int> = listarDezenas(dadosJSON.get("resultado").toString())
    val resultadoOrdenado: List<Int> = listarDezenas(dadosJSON.get("resultadoOrdenado").toString())

    val ganhadores: Int = dadosJSON.get("ganhadores").toString().toInt()
    val ganhadores_quina: Int = dadosJSON.get("ganhadores_quina").toString().toInt()
    val ganhadores_quadra: Int = dadosJSON.get("ganhadores_quadra").toString().toInt()

    val valor: Float = dadosJSON.get("valor").toString().toFloat()
    val valor_quina: Float = dadosJSON.get("valor_quina").toString().toFloat()
    val valor_quadra: Float = dadosJSON.get("valor_quadra").toString().toFloat()

    val acumulado: Boolean = dadosJSON.get("acumulado").toString().toBoolean()

    val valor_acumulado: Float = dadosJSON.get("valor_acumulado").toString().toFloat()
    val dtinclusao: String = dadosJSON.get("dtinclusao").toString()
    val prox_final_zero: Int = dadosJSON.get("prox_final_zero").toString().toInt()
    val ac_final_zero: Float = dadosJSON.get("ac_final_zero").toString().toFloat()
    val proxConcursoFinal: Int = dadosJSON.get("proxConcursoFinal").toString().toInt()
    val observacao: String = dadosJSON.get("observacao").toString()
    val ic_conferido: String = dadosJSON.get("ic_conferido").toString()
    val de_local_sorteio: String = dadosJSON.get("de_local_sorteio").toString()
    val no_cidade: String = dadosJSON.get("no_cidade").toString()
    val sg_uf: String = dadosJSON.get("sg_uf").toString()
    val vr_estimativa: Float = dadosJSON.get("vr_estimativa").toString().toFloat()
    val dt_proximo_concurso: String = dadosJSON.get("dt_proximo_concurso").toString()
    val vr_acumulado_especial: Float = dadosJSON.get("vr_acumulado_especial").toString().toFloat()
    val vr_arrecadado: Float = dadosJSON.get("vr_arrecadado").toString().toFloat()
    val ic_concurso_especial: String = dadosJSON.get("ic_concurso_especial").toString()
    val rateioProcessamento: String = dadosJSON.get("rateioProcessamento").toString()
    val sorteioAcumulado: String = dadosJSON.get("sorteioAcumulado").toString()
    val concursoEspecial: Boolean = dadosJSON.get("concursoEspecial").toString().toBoolean()

    val ganhadoresPorUf = listarDadosGanhadores(dadosJSON.get("ganhadoresPorUf"))

    private fun listarDezenas(dados: String, separador: Char = '-'): List<Int> {
        var lista = mutableListOf<Int>()
        for (d in dados.split(separador)){
            lista.add(d.toInt())
        }

        return lista
    }

    private fun listarDadosGanhadores(dados: Any): List<DadosGanhador> {
        var listaGanhadores = mutableListOf<DadosGanhador>()

        return listaGanhadores
    }

}

class DadosGanhador(private val dadosJSON: String){
    val proximoConcurso: String = ""
    val concursoAnterior: String = ""
    val forward: String = ""
    val coLoteria: String = ""
    val nuConcurso: String = ""
    val sgUf: String = ""
    val qtGanhadores: String = ""
    val noCidade: String = ""
    val total: String = ""
    val icCanalEletronico: String = ""
}