package com.example.gerenciamentodejogos.modelos

import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class TipoDeJogo {
    val MEGA_SENA = 0
    val QUINA = 1
    val LOTOFACIL = 2
    val LOTOMANIA = 3
    val DUPLA_SENA = 4
    val FEDERAL = 5
    val DIA_DE_SORTE = 6
    val TIMEMANIA = 7
    val LOTECA = 8
    val LOTOGOL = 9
}

open class DadosDoJogo(val tipoJogo: Int, val recursos: Resources) {
    val nome: String = recursos.getStringArray(R.array.nomes_jogos)[tipoJogo]
    val nomeEspecial: String = recursos.getStringArray(R.array.nomes_especiais)[tipoJogo]
    val url_resultado: String = recursos.getStringArray(R.array.urls_resultados)[tipoJogo]
    val corPrimaria:Int = Color.parseColor(recursos.getStringArray(R.array.cores_primarias)[tipoJogo])
    val corSecundaria:Int = Color.parseColor(recursos.getStringArray(R.array.cores_primarias)[tipoJogo])

    val dezenasSorteadas: Int = recursos.getIntArray(R.array.dezenas_sorteadas)[tipoJogo]
    val dezenasMinima: Int = recursos.getIntArray(R.array.dezenas_minima)[tipoJogo]
    val dezenasMaxima: Int = recursos.getIntArray(R.array.dezenas_maxima)[tipoJogo]
    val dezenasTotal: Int = recursos.getIntArray(R.array.dezenas_total)[tipoJogo]

    val textoEstimativaProximoConcurso: String = recursos.getString(R.string.texto_estimativa_proximo_concurso)
    val textoAcumuladoProximoConcurso: String = recursos.getString(R.string.texto_acumulado_proximo_concurso)
    val textoAcumuladoFinalX: String = recursos.getString(R.string.texto_acumulado_final_x)
    val textoAcumuladoConcursoEspecial: String = recursos.getString(R.string.texto_acumulado_especial)
    val textoValorArrecadacao: String = recursos.getString(R.string.texto_valor_arrecadado)

    protected val CARECTER_NULO = recursos.getString(R.string.caracter_nulo)

    protected val chaveDataConcurso: String get() {return recursos.getStringArray(R.array.dataConcurso)[tipoJogo]}
    protected val chaveDataProximoConcurso: String get() {return recursos.getStringArray(R.array.dataProximoConcurso)[tipoJogo]}

    protected val chaveQuantidadeGanhadores: String get() {return recursos.getStringArray(R.array.quantidade_ganhadores)[tipoJogo]}
    protected val chaveValoresPremiacoes: String get() {return recursos.getStringArray(R.array.valores_premiacoes)[tipoJogo]}
    protected val chaveFaixasPremiacoes: String get() {return recursos.getStringArray(R.array.faixas_premiacoes)[tipoJogo]}
    protected val chaveTextoFaixasPremiacoes: String get() {return recursos.getStringArray(R.array.texto_faixas_premiacoes)[tipoJogo]}

    protected val chaveAcumulou: String get() {return recursos.getStringArray(R.array.acumulou)[tipoJogo]}
    protected val chaveLocalSorteio: String get() {return recursos.getStringArray(R.array.localSorteio)[tipoJogo]}
    protected val chaveCidadeSorteio: String get() {return recursos.getStringArray(R.array.cidadeSorteio)[tipoJogo]}
    protected val chaveUfSorteio: String get() {return recursos.getStringArray(R.array.ufSorteio)[tipoJogo]}
    protected val chaveValorArrecadado: String get() {return recursos.getStringArray(R.array.valorArrecadado)[tipoJogo]}
    protected val chaveObservacao: String get() {return recursos.getStringArray(R.array.observacao)[tipoJogo]}
    protected val chaveErro: String get() {return recursos.getStringArray(R.array.erro)[tipoJogo]}
    protected val chaveValorAcumuladoProxConcurso: String get() {return recursos.getStringArray(R.array.valorAcumuladoProxConcurso)[tipoJogo]}
    protected val chaveEstimativaProxConcurso: String get() {return recursos.getStringArray(R.array.estimativaProxConcurso)[tipoJogo]}
    protected val chaveMensagens: String get() {return recursos.getStringArray(R.array.mensagens)[tipoJogo]}

    protected val chaveInfoGanhadores: String get() {return recursos.getStringArray(R.array.info_ganhadores)[tipoJogo]}
    protected val chaveInfoGanhadoresUf: String get() {return recursos.getStringArray(R.array.info_ganhadores_uf)[tipoJogo]}
    protected val chaveInfoGanhadoresCidade: String get() {return recursos.getStringArray(R.array.info_ganhadores_cidade)[tipoJogo]}
    protected val chaveInfoGanhadoresQuantidade: String get() {return recursos.getStringArray(R.array.info_ganhadores_quantidade)[tipoJogo]}
    protected val chaveInfoGanhadoresCanalEletronico: String get() {return recursos.getStringArray(R.array.info_ganhadores_canal_eletronico)[tipoJogo]}

    protected val chaveResultado: String get() {return recursos.getStringArray(R.array.resultado)[tipoJogo]}
    protected val chaveResultadoOrdenado: String get() {return recursos.getStringArray(R.array.resultado_ordenado)[tipoJogo]}
    protected val chaveResultadoAdicional: String get() {return recursos.getStringArray(R.array.resultado_adicional)[tipoJogo]}

    protected val chaveParametrosLoteca: Array<String> get() {return recursos.getStringArray(R.array.parametros_loteca)}
    protected val chaveParametrosLotogol: Array<String> get() {return recursos.getStringArray(R.array.parametros_lotogol)}

    protected val chaveConcursoEspecial: String get() {return recursos.getStringArray(R.array.concurso_especial)[tipoJogo]}
    protected val chaveValorAcumuladoEspecial: String get() {return recursos.getStringArray(R.array.valor_acumulado_especial)[tipoJogo]}

    protected val chaveValorAcumuladoFinalX: String get() {return recursos.getStringArray(R.array.valor_acumulado_final_x)[tipoJogo]}
    protected val chaveProximoConcursoFinalX: String get() {return recursos.getStringArray(R.array.proximo_concurso_final_x)[tipoJogo]}
}

class DadosJSON(stringJSON: String, lista: Boolean = false) {
    private val dados: JSONObject = if (lista) {
        val array = JSONArray(stringJSON)
        JSONObject(array.getJSONObject(0).toString())
    } else {
        JSONObject(stringJSON)
    }
    private var erro = false

    fun getString(chave: String): String{
        return try {
            dados.getString(chave)
        } catch (erro: Exception) {
            "ERRO!"
        }
    }

    fun getInt(chave: String): Int {
        return try {
            dados.getInt(chave)
        } catch (erro: Exception) {
            -1
        }
    }

    fun getDouble(chave: String): Double {
        return try {
            if (!dados.isNull(chave)) {
                dados.getDouble(chave)
            } else {
                0.0
            }
        } catch (erro: Exception) {
            var retorno = getString(chave)
            if (retorno != "ERRO!") {
                retorno = retorno.replace(".", "").replace(',', '.')
                try {
                    retorno.toDouble()
                } catch (erro: Exception) {
                    -0.1
                }
            } else {
                -0.1
            }
        }
    }

    fun getBoolean(chave: String): Boolean {
        return try {
            dados.getBoolean(chave)
        } catch (erro: Exception) {
            getInt(chave) == 1
        }
    }

    fun getLong(chave: String): Long {
        return try {
            dados.getLong(chave)
        } catch (erro: Exception) {
            -1
        }
    }

    fun getLista(chave: String): JSONArray{
        return try {
            dados.getJSONArray(chave)
        } catch (erro: Exception) {
            JSONArray()
        }
    }
}

open class Jogo(val concurso: Int, tipoJogo: Int, recursos: Resources): DadosDoJogo(tipoJogo, recursos) {
    data class ResultadoLotecaLotogol(val time1: String, val time2: String, val gols_time1: Int, val gols_time2: Int, val diaDaSemana: String)
    data class Valores(val texto: String, val valor: Double)
    data class Ganhadores(val uf: String, val cidade: String, val quantidade: Int, val meioEletronico: Boolean)
    data class Premiacoes(val numAcertos: Int, val textoFaixa: String, val numGanhadores: Int, val valorIndividual: Double) {
        val totalPago = numGanhadores * valorIndividual
    }

    private var dadosJSON: DadosJSON
    private var stringJSON: String
    init {
        stringJSON = buscalDadosLocalmente()
        if (stringJSON.isEmpty()) {
            stringJSON = buscarDadosWeb()
            salvarDadosLocalmente(stringJSON)
        }

        if (tipoJogo == TipoDeJogo().LOTOGOL) {
            dadosJSON = DadosJSON(stringJSON, true)
        } else {
            dadosJSON = DadosJSON(stringJSON)
        }
    }

    private val resAdicional: String = dadosJSON.getString(chaveResultadoAdicional)

    private fun obterFaixasPremiacoes(): List<Int> {
        val faixasPremiacoes: List<String> = chaveFaixasPremiacoes.split("|")
        val lista: MutableList<Int> = mutableListOf()

        for (chave in faixasPremiacoes) {
            lista.add(chave.toInt())
        }
        return lista
    }
    private fun obterTextoFaixasPremiacoes(): List<String> {
        return chaveTextoFaixasPremiacoes.split("|")
    }
    private fun obterQuantidadeGanhadores(): List<Int> {
        val chavesQuantGanhadores: List<String> = chaveQuantidadeGanhadores.split("|")
        val lista: MutableList<Int> = mutableListOf()

        for (chave in chavesQuantGanhadores) {
            var valor = 0
            try {
                valor = dadosJSON.getInt(chave)
            } catch (ex: Exception) {
                Log.e("MEGASENA/obterQuantidadeGanhadores", ex.message)
            }
            lista.add(valor)
        }
        return lista
    }
    private fun obterValoresPremiacoes(): List<Double> {
        val chavesValoresPremiacoes: List<String> = chaveValoresPremiacoes.split("|")
        val lista: MutableList<Double> = mutableListOf()

        for (chave in chavesValoresPremiacoes) {
            var valor = 0.0
            try {
                valor = dadosJSON.getDouble(chave)
            } catch (ex: Exception) {
                Log.e("MEGASENA/obterValoresPremiacoes", ex.message)
            }
            lista.add(valor)
        }
        return lista
    }
    private fun calcularValorTotalPremiacoes(premiacoes: List<Premiacoes>): Double {
        var total = 0.0
        premiacoes.forEach {premio ->
            total += premio.totalPago
        }
        return total
    }

    private fun gerarListaPremiacoes(): List<Premiacoes> {
        val faixas = obterFaixasPremiacoes()
        val textoFaixas = obterTextoFaixasPremiacoes()
        val quantGanhadores = obterQuantidadeGanhadores()
        val valoresPremios = obterValoresPremiacoes()

        val lista: MutableList<Premiacoes> = mutableListOf()
        faixas.forEachIndexed { index, faixa ->
            lista.add(Premiacoes(faixa, textoFaixas[index], quantGanhadores[index], valoresPremios[index]))
        }
        return lista
    }
    private fun gerarListaGanhadores(): List<Ganhadores> {
        val listaGanhadores = dadosJSON.getLista(chaveInfoGanhadores)
        val lista: MutableList<Ganhadores> = mutableListOf()

        for (g in 0..listaGanhadores.length() - 1) {
            val dados = DadosJSON(listaGanhadores.getJSONObject(g).toString())
            val uf = dados.getString(chaveInfoGanhadoresUf)
            val cidade = dados.getString(chaveInfoGanhadoresCidade)
            val quant = dados.getInt(chaveInfoGanhadoresQuantidade)
            val canalElet = dados.getBoolean(chaveInfoGanhadoresCanalEletronico)

            lista.add(Ganhadores(uf, cidade, quant, canalElet))
        }
        return lista

    }

    private fun gerarResultadosLotecaLotogol(): List<ResultadoLotecaLotogol>{
        val lista: MutableList<ResultadoLotecaLotogol> = mutableListOf()

        when (tipoJogo) {
            TipoDeJogo().LOTECA -> {
                val listaJogos = dadosJSON.getLista(chaveResultado)

                for (j in 0..listaJogos.length() - 1) {
                    val dados = DadosJSON(listaJogos.getJSONObject(j).toString())

                    val time1 = dados.getString(chaveParametrosLoteca[0])
                    val time2 = dados.getString(chaveParametrosLoteca[1])
                    val gols_time1 = dados.getString(chaveParametrosLoteca[2])
                    val gols_time2 = dados.getString(chaveParametrosLoteca[3])
                    val diaDaSemana = dados.getString(chaveParametrosLoteca[4])

                    lista.add(ResultadoLotecaLotogol(time1, time2, gols_time1.toInt(), gols_time2.toInt(), diaDaSemana))
                }
            }
            TipoDeJogo().LOTOGOL -> {
                val listaJogos = JSONArray(stringJSON)

                for (j in 0..listaJogos.length() - 1) {
                    val dados = DadosJSON(listaJogos.getJSONObject(j).toString())

                    val time1 = dados.getString(chaveParametrosLotogol[0])
                    val time2 = dados.getString(chaveParametrosLotogol[1])
                    val gols_time1 = dados.getString(chaveParametrosLotogol[2])
                    val gols_time2 = dados.getString(chaveParametrosLotogol[3])
                    val diaDaSemana = dados.getString(chaveParametrosLotogol[4])

                    lista.add(ResultadoLotecaLotogol(time1, time2, gols_time1.toInt(), gols_time2.toInt(), diaDaSemana))
                }
            }
        }
        return lista
    }

    private fun gerarListaResultados(ordenado: Boolean = true, separador: Char = '-'): List<String> {
        return if (ordenado) {
            dadosJSON.getString(chaveResultadoOrdenado)
        } else {
            dadosJSON.getString(chaveResultado)
        }.split(separador)
    }
    fun ListaValoresTotais(): List<Valores> {
        val lista = mutableListOf<Valores>()

        var valor = Valores(textoEstimativaProximoConcurso, estimativaProxConcurso)
        lista.add(valor)

        valor = Valores(textoAcumuladoProximoConcurso, valorAcumuladoProxConcurso)

        lista.add(valor)
        if (temConcursoFinalX) {
            val final = proximoConcusoFinalX.toString().last()
            valor = Valores("${textoAcumuladoFinalX} ${final} (${proximoConcusoFinalX})", valorAcumuladoFinalX)
            lista.add(valor)
        }

        if (temConcursoEspecial) {
            valor = Valores("${textoAcumuladoConcursoEspecial} ${nomeEspecial}:", valorAcumuladoEspecial)
            lista.add(valor)
        }

        valor = Valores(textoValorArrecadacao, valorArrecadado)
        lista.add(valor)

        return lista
    }

    val dataConcurso: Long = dadosJSON.getLong(chaveDataConcurso)
    val dataProximoConcurso: Long = dadosJSON.getLong(chaveDataProximoConcurso)

    val acumulou: Boolean = dadosJSON.getBoolean(chaveAcumulou)

    val localSorteio: String = dadosJSON.getString(chaveLocalSorteio)
    val cidadeSorteio: String = dadosJSON.getString(chaveCidadeSorteio)
    val ufSorteio: String = dadosJSON.getString(chaveUfSorteio)

    val valorArrecadado: Double = dadosJSON.getDouble(chaveValorArrecadado)
    val valorAcumuladoProxConcurso: Double = dadosJSON.getDouble(chaveValorAcumuladoProxConcurso)
    val estimativaProxConcurso: Double = dadosJSON.getDouble(chaveEstimativaProxConcurso)

    val observacao: String = dadosJSON.getString(chaveObservacao)
    val erro: String = dadosJSON.getString(chaveErro)

    val premiacoes: List<Premiacoes> = gerarListaPremiacoes()
    val valorTotalPremiacoes: Double = calcularValorTotalPremiacoes(premiacoes)

    val ganhadores: List<Ganhadores> = gerarListaGanhadores()

    val resultado: List<String> = gerarListaResultados(false)
    val resultadoOrdenado: List<String> = gerarListaResultados(true)
    val resultadoAdicional: String = if (resAdicional != "ERRO!") resAdicional else ""

    val resultadoLotogolLoteca: List<ResultadoLotecaLotogol> = gerarResultadosLotecaLotogol()

    val temConcursoEspecial: Boolean = (chaveConcursoEspecial != CARECTER_NULO)
    val concursoEspecial: Boolean = dadosJSON.getBoolean(chaveConcursoEspecial)
    val valorAcumuladoEspecial: Double = dadosJSON.getDouble(chaveValorAcumuladoEspecial)

    val temConcursoFinalX: Boolean = (chaveValorAcumuladoFinalX != CARECTER_NULO)
    val valorAcumuladoFinalX: Double = dadosJSON.getDouble(chaveValorAcumuladoFinalX)
    val proximoConcusoFinalX: Int = dadosJSON.getInt(chaveProximoConcursoFinalX)

    //    TODO - DEFINIR COMO SERÁ O RETORNO DE 'encerrado'
    val encerrado: Boolean = true


//    TODO - OBTER LISTA DE MENSAGENS
//    val mensagens: List<String> = listOf()

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

    protected fun obterURLResultado(): String {
        var url_temp = url_resultado
        url_temp += recursos.getString(R.string.url_parametro_1) + Date().time
        url_temp += recursos.getString(R.string.url_parametro_2) + concurso.toString()
        return url_temp
    }
}
