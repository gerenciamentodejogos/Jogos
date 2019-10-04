package br.com.dev.jogosdaloteria.modelos

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.dados_web.DadosWeb
import br.com.dev.jogosdaloteria.persistencia.IPersistencia
import br.com.dev.jogosdaloteria.persistencia.PersistenciaSQLite
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

final class TipoDeJogo {
    companion object {
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
}

@Entity(tableName = "TBResultadoJSON", primaryKeys = arrayOf("tipoJogo", "numConcurso"))
class Jogo(val tipoJogo: Int,
           val numConcurso: Int,
           val resultado: String)

open class DadosDoJogo(val tipoJogo: Int, val contexto: Context) {
    val nome: String = contexto.resources.getStringArray(R.array.nomes_jogos)[tipoJogo]
    val nomeEspecial: String = contexto.resources.getStringArray(R.array.nomes_especiais)[tipoJogo]
    val url_resultado: String = contexto.resources.getStringArray(R.array.urls_resultados)[tipoJogo]
    val corPrimaria:Int = Color.parseColor(contexto.resources.getStringArray(R.array.cores_primarias)[tipoJogo])
    val corSecundaria:Int = Color.parseColor(contexto.resources.getStringArray(R.array.cores_primarias)[tipoJogo])

    val dezenasSorteadas: Int = contexto.resources.getIntArray(R.array.dezenas_sorteadas)[tipoJogo]
    val dezenasMinima: Int = contexto.resources.getIntArray(R.array.dezenas_minima)[tipoJogo]
    val dezenasMaxima: Int = contexto.resources.getIntArray(R.array.dezenas_maxima)[tipoJogo]
    val dezenasTotal: Int = contexto.resources.getIntArray(R.array.dezenas_total)[tipoJogo]

    val textoEstimativaProximoConcurso: String = contexto.resources.getString(R.string.texto_estimativa_proximo_concurso)
    val textoAcumuladoProximoConcurso: String = contexto.resources.getString(R.string.texto_acumulado_proximo_concurso)
    val textoAcumuladoFinalX: String = contexto.resources.getString(R.string.texto_acumulado_final_x)
    val textoAcumuladoConcursoEspecial: String = contexto.resources.getString(R.string.texto_acumulado_especial)
    val textoValorArrecadacao: String = contexto.resources.getString(R.string.texto_valor_arrecadado)

    val textoResultadoAdicinal: String = contexto.resources.getStringArray(R.array.texto_resultado_adicional)[tipoJogo]

    protected val CARECTER_NULO = contexto.resources.getString(R.string.caracter_nulo)

    protected val chaveDataConcurso: String get() {return contexto.resources.getStringArray(R.array.dataConcurso)[tipoJogo]}
    protected val chaveDataProximoConcurso: String get() {return contexto.resources.getStringArray(R.array.dataProximoConcurso)[tipoJogo]}

    protected val chaveQuantidadeGanhadores: String get() {return contexto.resources.getStringArray(R.array.quantidade_ganhadores)[tipoJogo]}
    protected val chaveValoresPremiacoes: String get() {return contexto.resources.getStringArray(R.array.valores_premiacoes)[tipoJogo]}
    protected val chaveFaixasPremiacoes: String get() {return contexto.resources.getStringArray(R.array.faixas_premiacoes)[tipoJogo]}
    protected val chaveTextoFaixasPremiacoes: String get() {return contexto.resources.getStringArray(R.array.texto_faixas_premiacoes)[tipoJogo]}

    protected val chaveAcumulou: String get() {return contexto.resources.getStringArray(R.array.acumulou)[tipoJogo]}
    protected val chaveLocalSorteio: String get() {return contexto.resources.getStringArray(R.array.localSorteio)[tipoJogo]}
    protected val chaveCidadeSorteio: String get() {return contexto.resources.getStringArray(R.array.cidadeSorteio)[tipoJogo]}
    protected val chaveUfSorteio: String get() {return contexto.resources.getStringArray(R.array.ufSorteio)[tipoJogo]}
    protected val chaveValorArrecadado: String get() {return contexto.resources.getStringArray(R.array.valorArrecadado)[tipoJogo]}
    protected val chaveObservacao: String get() {return contexto.resources.getStringArray(R.array.observacao)[tipoJogo]}
    protected val chaveErro: String get() {return contexto.resources.getStringArray(R.array.erro)[tipoJogo]}
    protected val chaveValorAcumuladoProxConcurso: String get() {return contexto.resources.getStringArray(R.array.valorAcumuladoProxConcurso)[tipoJogo]}
    protected val chaveEstimativaProxConcurso: String get() {return contexto.resources.getStringArray(R.array.estimativaProxConcurso)[tipoJogo]}
    protected val chaveMensagens: String get() {return contexto.resources.getStringArray(R.array.mensagens)[tipoJogo]}

    protected val chaveInfoGanhadores: String get() {return contexto.resources.getStringArray(R.array.info_ganhadores)[tipoJogo]}
    protected val chaveInfoGanhadoresUf: String get() {return contexto.resources.getStringArray(R.array.info_ganhadores_uf)[tipoJogo]}
    protected val chaveInfoGanhadoresCidade: String get() {return contexto.resources.getStringArray(R.array.info_ganhadores_cidade)[tipoJogo]}
    protected val chaveInfoGanhadoresQuantidade: String get() {return contexto.resources.getStringArray(R.array.info_ganhadores_quantidade)[tipoJogo]}
    protected val chaveInfoGanhadoresCanalEletronico: String get() {return contexto.resources.getStringArray(R.array.info_ganhadores_canal_eletronico)[tipoJogo]}

    protected val chaveResultado: String get() {return contexto.resources.getStringArray(R.array.resultado)[tipoJogo]}
    protected val chaveResultadoOrdenado: String get() {return contexto.resources.getStringArray(R.array.resultado_ordenado)[tipoJogo]}
    protected val chaveResultadoAdicional: String get() {return contexto.resources.getStringArray(R.array.resultado_adicional)[tipoJogo]}

    protected val chaveParametrosFederal: Array<String> get() {return contexto.resources.getStringArray(R.array.parametros_federal)}
    protected val chaveParametrosLoteca: Array<String> get() {return contexto.resources.getStringArray(R.array.parametros_loteca)}
    protected val chaveParametrosLotogol: Array<String> get() {return contexto.resources.getStringArray(R.array.parametros_lotogol)}

    protected val chaveConcursoEspecial: String get() {return contexto.resources.getStringArray(R.array.concurso_especial)[tipoJogo]}
    protected val chaveValorAcumuladoEspecial: String get() {return contexto.resources.getStringArray(R.array.valor_acumulado_especial)[tipoJogo]}

    protected val chaveValorAcumuladoFinalX: String get() {return contexto.resources.getStringArray(R.array.valor_acumulado_final_x)[tipoJogo]}
    protected val chaveProximoConcursoFinalX: String get() {return contexto.resources.getStringArray(R.array.proximo_concurso_final_x)[tipoJogo]}
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

open class Resultado(val concurso: Int, tipoJogo: Int, var context: Context): DadosDoJogo(tipoJogo, context) {

    private val persistencia: IPersistencia = PersistenciaSQLite(context)
    private var dadoLocal = false

    data class ResultadoLotecaLotogol(val time1: String, val time2: String, val gols_time1: Int, val gols_time2: Int, val diaDaSemana: String)
    data class ResultadoFederal(val destino: String, val bilhete: String, val valor: String)
    data class Valores(val texto: String, val valor: Double)
    data class Ganhadores(val uf: String, val cidade: String, val quantidade: Int, val meioEletronico: Boolean){
        fun getLocalGanhadores(): String {
            var retorno = ""

            if (meioEletronico) {
                retorno = "Canal eletrônico"
            } else {
                retorno = "$cidade - $uf"
                retorno = retorno.replace("null - ", "")
                retorno = retorno.replace(" - null", "")
            }
            return retorno
        }
    }
    data class Premiacoes(val numAcertos: Int, val textoFaixa: String, val numGanhadores: Int, val valorIndividual: Double) {
        val totalPago = numGanhadores * valorIndividual
    }

    private val stringJSON = buscarDados()?.let {
        Log.e("BUSCA", "Dados locais: $nome - $concurso")
        dadoLocal = true
        it
    }?: buscarDadosWeb()

    private val dadosJSON = if (tipoJogo == TipoDeJogo.LOTOGOL) {
        DadosJSON(stringJSON, true)
    } else {
        DadosJSON(stringJSON)
    }

    val erro: Boolean = dadosJSON.getBoolean(chaveErro)

    init {
        if (!dadoLocal && !erro) salvarDados(stringJSON)
        if (tipoJogo == TipoDeJogo.LOTOGOL) {
            val a = tipoJogo
        }
    }

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
        val lista: MutableList<Premiacoes> = mutableListOf()
        if (tipoJogo != TipoDeJogo.FEDERAL && tipoJogo != TipoDeJogo.DUPLA_SENA) {
            val faixas = obterFaixasPremiacoes()
            val textoFaixas = obterTextoFaixasPremiacoes()
            val quantGanhadores = obterQuantidadeGanhadores()
            val valoresPremios = obterValoresPremiacoes()

            faixas.forEachIndexed { index, faixa ->
                lista.add(
                    Premiacoes(
                        faixa,
                        textoFaixas[index],
                        quantGanhadores[index],
                        valoresPremios[index]
                    )
                )
            }
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

    private fun gerarResultadoAdicional(ordenado: Boolean = true, separador: Char = '-'): List<String> {
        return if (tipoJogo == TipoDeJogo.DUPLA_SENA) {
            val resultado = dadosJSON.getString(chaveResultadoOrdenado.split("#")[1])


            resultado.split("-").toList()
        } else {
            val resultado = dadosJSON.getString(chaveResultadoAdicional)
            if (resultado == "ERRO!") {
                listOf()
            } else {
                listOf(resultado)
            }
        }
    }

    private fun gerarResultadosFederal(): List<ResultadoFederal> {
        val lista: MutableList<ResultadoFederal> = mutableListOf()

        val listaJogos = dadosJSON.getLista(chaveResultado)

        for (j in 0 until listaJogos.length()) {
            val dados = DadosJSON(listaJogos.getJSONObject(j).toString())

            val destino = dados.getString(chaveParametrosFederal[0])
            val bilhete = dados.getString(chaveParametrosFederal[1])
            val valor = dados.getString(chaveParametrosFederal[2])

            lista.add(ResultadoFederal(destino, bilhete, valor))
        }
        return lista
    }

    private fun gerarResultadosLotecaLotogol(): List<ResultadoLotecaLotogol>{
        val lista: MutableList<ResultadoLotecaLotogol> = mutableListOf()

        when (tipoJogo) {
            TipoDeJogo.LOTECA -> {
                val listaJogos = dadosJSON.getLista(chaveResultado)

                for (j in 0..listaJogos.length() - 1) {
                    val dados = DadosJSON(listaJogos.getJSONObject(j).toString())

                    val time1 = dados.getString(chaveParametrosLoteca[0])
                    val time2 = dados.getString(chaveParametrosLoteca[1])
                    val gols_time1 = dados.getString(chaveParametrosLoteca[2])
                    val gols_time2 = dados.getString(chaveParametrosLoteca[3])
                    val diaDaSemana = dados.getString(chaveParametrosLoteca[4])

                    lista.add(
                        ResultadoLotecaLotogol(
                            time1,
                            time2,
                            gols_time1.toInt(),
                            gols_time2.toInt(),
                            diaDaSemana
                        )
                    )
                }
            }
            TipoDeJogo.LOTOGOL -> {
                val listaJogos = JSONArray(stringJSON)

                for (j in 0..listaJogos.length() - 1) {
                    val dados = DadosJSON(listaJogos.getJSONObject(j).toString())

                    val time1 = dados.getString(chaveParametrosLotogol[0])
                    val time2 = dados.getString(chaveParametrosLotogol[1])
                    val gols_time1 = dados.getString(chaveParametrosLotogol[2])
                    val gols_time2 = dados.getString(chaveParametrosLotogol[3])
                    val diaDaSemana = dados.getString(chaveParametrosLotogol[4])

                    lista.add(
                        ResultadoLotecaLotogol(
                            time1,
                            time2,
                            gols_time1.toInt(),
                            gols_time2.toInt(),
                            diaDaSemana
                        )
                    )
                }
            }
        }
        return lista
    }

    private fun gerarListaResultados(ordenado: Boolean = true, separador: Char = '-'): List<String> {
        return if (ordenado) {
            if (tipoJogo == TipoDeJogo.DUPLA_SENA) {
                dadosJSON.getString(chaveResultadoOrdenado.split("#")[0])
            } else {
                dadosJSON.getString(chaveResultadoOrdenado)
            }
        } else {
            if (tipoJogo == TipoDeJogo.DUPLA_SENA) {
                dadosJSON.getString(chaveResultado.split("#")[0])
            } else {
                dadosJSON.getString(chaveResultado)
            }

        }.split(separador)
    }
    fun ListaValoresTotais(): List<Valores> {
        val lista = mutableListOf<Valores>()

        var valor =
            Valores(textoEstimativaProximoConcurso, estimativaProxConcurso)
        lista.add(valor)

        valor =
            Valores(textoAcumuladoProximoConcurso, valorAcumuladoProxConcurso)

        lista.add(valor)
        if (temConcursoFinalX) {
            val final = proximoConcusoFinalX.toString().last()
            valor = Valores(
                "${textoAcumuladoFinalX} ${final} (${proximoConcusoFinalX})",
                valorAcumuladoFinalX
            )
            lista.add(valor)
        }

        if (temConcursoEspecial) {
            valor = Valores(
                "${textoAcumuladoConcursoEspecial} ${nomeEspecial}:",
                valorAcumuladoEspecial
            )
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

    val premiacoes: List<Premiacoes> = gerarListaPremiacoes()
    val valorTotalPremiacoes: Double = calcularValorTotalPremiacoes(premiacoes)

    val ganhadores: List<Ganhadores> = gerarListaGanhadores()

    val resultado: List<String> = gerarListaResultados(false)
    val resultadoOrdenado: List<String> = gerarListaResultados(true)
    val resultadoAdicional: List<String> = gerarResultadoAdicional()
    val resultadoAdicionalOrdenado: List<String> = gerarResultadoAdicional(true)

    val resultadoLotogolLoteca: List<ResultadoLotecaLotogol> = gerarResultadosLotecaLotogol()
    val resultadoFederal: List<ResultadoFederal> = gerarResultadosFederal()

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

    private fun salvarDados(stringJSON: String) {
        persistencia.salvarResultado(tipoJogo, concurso, stringJSON)
    }

    private fun buscarDados() = persistencia.buscarResultado(tipoJogo, concurso)

    private fun buscarDadosWeb(): String {
        var stringJSON = DadosWeb(obterURLResultado()).ObterResultados()
        stringJSON = stringJSON.substringAfter("<body>", stringJSON)
        stringJSON = stringJSON.substringBefore("</body>", stringJSON).trim()

        return stringJSON
    }

    protected fun obterURLResultado(): String {
        var url_temp = url_resultado
        url_temp += contexto.getString(R.string.url_parametro_1) + Date().time
        url_temp += contexto.getString(R.string.url_parametro_2) + concurso.toString()
        return url_temp
    }
}

