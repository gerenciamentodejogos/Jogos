package com.example.gerenciamentodejogos.modelos

import android.content.res.Resources
import android.content.res.Resources.getSystem
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import org.json.JSONObject
import java.util.*


class Jogo(val numConcurso: Int, val tipoJogo: Int = 0, val url: String, val parametro1: String, val parametro2: String) {
    var encerrado = false
    var premioEstimado = 0

    val dadosResultado = ObterDados()

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

    private var dadosJSON = JSONObject()

    init {
        stringJSON = stringJSON.substringAfter("<body>", stringJSON)
        stringJSON = stringJSON.substringBefore("</body>", stringJSON).trim()

        dadosJSON = JSONObject(stringJSON)
    }

    val proximoConcurso = dadosJSON.get("proximoConcurso").toString()
    val concursoAnterior = dadosJSON.get("concursoAnterior").toString()
    val concurso = dadosJSON.get("concurso").toString()
    val data = dadosJSON.get("data").toString()
    val resultado = dadosJSON.get("resultado").toString()
    val ganhadores = dadosJSON.get("ganhadores").toString()
    val ganhadores_quina = dadosJSON.get("ganhadores_quina").toString()
    val ganhadores_quadra = dadosJSON.get("ganhadores_quadra").toString()
    val valor = dadosJSON.get("valor").toString()
    val valor_quina = dadosJSON.get("valor_quina").toString()
    val valor_quadra = dadosJSON.get("valor_quadra").toString()
    val acumulado = dadosJSON.get("acumulado").toString()
    val valor_acumulado = dadosJSON.get("valor_acumulado").toString()
    val dtinclusao = dadosJSON.get("dtinclusao").toString()
    val prox_final_zero = dadosJSON.get("prox_final_zero").toString()
    val ac_final_zero = dadosJSON.get("ac_final_zero").toString()
    val proxConcursoFinal = dadosJSON.get("proxConcursoFinal").toString()
    val observacao = dadosJSON.get("observacao").toString()
    val ic_conferido = dadosJSON.get("ic_conferido").toString()
    val de_local_sorteio = dadosJSON.get("de_local_sorteio").toString()
    val no_cidade = dadosJSON.get("no_cidade").toString()
    val sg_uf = dadosJSON.get("sg_uf").toString()
    val vr_estimativa = dadosJSON.get("vr_estimativa").toString()
    val dt_proximo_concurso = dadosJSON.get("dt_proximo_concurso").toString()
    val vr_acumulado_especial = dadosJSON.get("vr_acumulado_especial").toString()
    val vr_arrecadado = dadosJSON.get("vr_arrecadado").toString()
    val ic_concurso_especial = dadosJSON.get("ic_concurso_especial").toString()
    val rateioProcessamento = dadosJSON.get("rateioProcessamento").toString()
    val sorteioAcumulado = dadosJSON.get("sorteioAcumulado").toString()
    val concursoEspecial = dadosJSON.get("concursoEspecial").toString()
    val resultadoOrdenado = dadosJSON.get("resultadoOrdenado").toString()
    val dataStr = dadosJSON.get("dataStr").toString()
    val dt_proximo_concursoStr = dadosJSON.get("dt_proximo_concursoStr").toString()

    val ganhadoresPorUf = listarDadosGanhadores(dadosJSON.get("ganhadoresPorUf"))


    //val forward = dadosJSON.get("forward").toString()
    //val rowguid = dadosJSON.get("rowguid").toString()
    //val error = dadosJSON.get("error").toString()

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