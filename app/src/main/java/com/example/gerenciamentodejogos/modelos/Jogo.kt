package com.example.gerenciamentodejogos.modelos

import android.content.res.Resources
import android.graphics.Color
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.dados_web.DadosWeb
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

    val acertos: List<Int>
        get() {
            val retorno = mutableListOf<Int>()
            val lista_acertos = recursos.getStringArray(R.array.acertos)[tipoJogo]
            for (a in lista_acertos.split('-')) {
                retorno.add(a.toInt())
            }
            return retorno
        }

    val chaveDataConcurso: String get() {return recursos.getStringArray(R.array.dataConcurso)[tipoJogo]}
    val chaveDataProximoConcurso: String get() {return recursos.getStringArray(R.array.dataProximoConcurso)[tipoJogo]}
    val chaveGanhadores: String get() {return recursos.getStringArray(R.array.ganhadores)[tipoJogo]}
    val chaveValorPremioPrincipal: String get() {return recursos.getStringArray(R.array.valorPremioPrincipal)[tipoJogo]}
    val chaveAcumulou: String get() {return recursos.getStringArray(R.array.acumulou)[tipoJogo]}
    val chaveLocalSorteio: String get() {return recursos.getStringArray(R.array.localSorteio)[tipoJogo]}
    val chaveCidadeSorteio: String get() {return recursos.getStringArray(R.array.cidadeSorteio)[tipoJogo]}
    val chaveUfSorteio: String get() {return recursos.getStringArray(R.array.ufSorteio)[tipoJogo]}
    val chaveValorArrecadado: String get() {return recursos.getStringArray(R.array.valorArrecadado)[tipoJogo]}
    val chaveObservacao: String get() {return recursos.getStringArray(R.array.observacao)[tipoJogo]}
    val chaveErro: String get() {return recursos.getStringArray(R.array.erro)[tipoJogo]}
    val chaveValorAcumuladoProxConcurso: String get() {return recursos.getStringArray(R.array.valorAcumuladoProxConcurso)[tipoJogo]}
    val chaveEstimativaProxConcurso: String get() {return recursos.getStringArray(R.array.estimativaProxConcurso)[tipoJogo]}
    val chaveMensagens: String get() {return recursos.getStringArray(R.array.mensagens)[tipoJogo]}
    val chaveInfoGanhadores: String get() {return recursos.getStringArray(R.array.infoGanhadores)[tipoJogo]}
}

class DadosJSON(stringJSON: String) {

    private val dados: JSONObject = JSONObject(stringJSON)

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
            dados.getDouble(chave)
        } catch (erro: Exception) {
            -1.1
        }
    }

    fun getBoolean(chave: String): Boolean {
        return try {
            dados.getBoolean(chave)
        } catch (erro: Exception) {
            false
        }
    }

    fun getLong(chave: String): Long {
        return try {
            dados.getLong(chave)
        } catch (erro: Exception) {
            -1
        }
    }
}


open class Jogo(val concurso: Int, tipoJogo: Int, recursos: Resources): DadosDoJogo(tipoJogo, recursos) {
    data class InfoGanhadores(val uf: String, val cidade: String, val quantidade: Int, val meioEletronico: Boolean)

    protected var dadosJSON: DadosJSON

    init {
        var stringJSON = buscalDadosLocalmente()
        if (stringJSON.isEmpty()) {
            stringJSON = buscarDadosWeb()
            salvarDadosLocalmente(stringJSON)
        }

        dadosJSON = DadosJSON(stringJSON)
    }

    val dataConcurso: Long = dadosJSON.getLong(chaveDataConcurso)
    val dataProximoConcurso: Long = dadosJSON.getLong(chaveDataProximoConcurso)

    val ganhadores: Int = dadosJSON.getInt(chaveGanhadores)
    val valorPremioPrincipal: Double = dadosJSON.getDouble("valor")
    val acumulou: Boolean = dadosJSON.getBoolean(chaveAcumulou)
    val localSorteio: String = dadosJSON.getString("de_local_sorteio")
    val cidadeSorteio: String = dadosJSON.getString("no_cidade")
    val ufSorteio: String = dadosJSON.getString("sg_uf")
    val valorArrecadado: Double = dadosJSON.getDouble("vrArrecadado")
    val observacao: String = dadosJSON.getString("observacao")
    val erro: String = dadosJSON.getString("error")
    val valorAcumuladoProxConcurso: Double = dadosJSON.getDouble("vrAcumulado")
    val estimativaProxConcurso: Double = dadosJSON.getDouble("vrEstimado")

    val valorTotalPremioPrincipal: Double get() = valorPremioPrincipal * ganhadores

    //    TODO - DEFINIR COMO SERÁ O RETORNO DE 'encerrado'
    val encerrado: Boolean = true


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


class MegaSena(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().MEGA_SENA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON.getString("resultado"))
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON.getString("resultadoOrdenado"))

    val ganhadoresQuina: Int = dadosJSON.getInt("ganhadores_quina")
    val ganhadoresQuadra: Int = dadosJSON.getInt("ganhadores_quadra")
    val valorQuina: Double = dadosJSON.getDouble("valor_quina")
    val valorQuadra : Double = dadosJSON.getDouble("valor_quadra")

    val concursoEspecial: Int = dadosJSON.getInt("concursoEspecial")
    val acumuladoEapecial: Double = dadosJSON.getDouble("vr_acumulado_especial")

    val valorTotalQuina: Double get() = valorQuina * ganhadoresQuina
    val valorTotalQuadra: Double get() = valorQuadra * ganhadoresQuadra
}

class Quina(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().QUINA, recursos) {
    val resultado: List<String> = listarResultado(dadosJSON.getString("resultado"))
    val resultadoOrdenado: List<String> = listarResultado(dadosJSON.getString("resultadoOrdenado"))

    val ganhadoresQuadra: Int = dadosJSON.getInt("ganhadores_quadra")
    val ganhadoresTerno: Int = dadosJSON.getInt("ganhadores_terno")
    val ganhadoresDuque: Int = dadosJSON.getInt("qt_ganhador_duque")

    val valorQuadra: Double = dadosJSON.getDouble("valor_quadra")
    val valorTerno: Double = dadosJSON.getDouble("valor_terno")
    val valorDuque: Double = dadosJSON.getDouble("vr_rateio_duque")

    val concursoEspecial: Boolean = dadosJSON.getBoolean("quinaSaoJoao")
    val acumuladoEapecial: Double = dadosJSON.getDouble("vrAcumuladoEspecial")

    val valorTotalQuadra: Double get() = valorQuadra * ganhadoresQuadra
    val valorTotalTerno: Double get() = valorTerno * ganhadoresTerno
    val valorTotalDuque: Double get() = valorDuque * ganhadoresDuque
}

class Lotofacil(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().LOTOFACIL, recursos)
class Lotomania(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().LOTOMANIA, recursos)
class DuplaSena(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().DUPLA_SENA, recursos)
class Federal(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().FEDERAL, recursos)
class DiaDeSorte(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().DIA_DE_SORTE, recursos)
class Timemania(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().TIMEMANIA, recursos)
class Loteca(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().LOTECA, recursos)
class Lotogol(val numConcurso: Int, recursos: Resources): Jogo(numConcurso, TipoDeJogo().LOTOGOL, recursos)

final class InstanciarJogo {

    fun get(numConcurso: Int, tipoJogo: Int, recursos: Resources): Jogo {
        return when (tipoJogo) {
            TipoDeJogo().MEGA_SENA -> MegaSena(numConcurso, recursos)
            TipoDeJogo().QUINA -> Quina(numConcurso, recursos)
            TipoDeJogo().LOTOFACIL -> Lotofacil(numConcurso, recursos)
            TipoDeJogo().LOTOMANIA -> Lotomania(numConcurso, recursos)
            TipoDeJogo().DUPLA_SENA -> DuplaSena(numConcurso, recursos)
            TipoDeJogo().FEDERAL -> Federal(numConcurso, recursos)
            TipoDeJogo().DIA_DE_SORTE -> DiaDeSorte(numConcurso, recursos)
            TipoDeJogo().TIMEMANIA -> Timemania(numConcurso, recursos)
            TipoDeJogo().LOTECA -> Loteca(numConcurso, recursos)
            TipoDeJogo().LOTOGOL -> Lotogol(numConcurso, recursos)
            else -> MegaSena(numConcurso, recursos)
        }
    }
}