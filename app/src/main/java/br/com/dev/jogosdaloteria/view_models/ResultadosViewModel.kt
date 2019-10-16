package br.com.dev.jogosdaloteria.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.TELA_INICIAL
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo
import br.com.dev.jogosdaloteria.modelos.Resultado
import br.com.dev.jogosdaloteria.modelos.TipoDeJogo
import br.com.dev.jogosdaloteria.modelos.Usuario
import br.com.dev.jogosdaloteria.persistencia.IPersistencia
import br.com.dev.jogosdaloteria.persistencia.JogosDB
import br.com.dev.jogosdaloteria.persistencia.PersistSQLite
import br.com.dev.jogosdaloteria.persistencia.PersistenciaSQLite

class ResultadosViewModel(application: Application): AndroidViewModel(application) {
    data class DadosLogin(val email: String, val senha: String)
    private val persist: PersistSQLite

    init {
        val db = JogosDB.get(application.applicationContext)
        persist = PersistSQLite(db.jogosDAO())
    }

    var propriedadesDosJogos: List<DadosDoJogo> = listOf()
    val usuarioLogado = MutableLiveData<Boolean>().apply { value = false }
    val usuario = MutableLiveData<Usuario>()
    val logar = MutableLiveData<Boolean>().apply { value = false }
    var dadosLogin = MutableLiveData<DadosLogin>()

    val jogos = MutableLiveData<List<Resultado>>()

    val tela = MutableLiveData<Int>().apply { value = TELA_INICIAL }

    val tipoResultadoSelecionado = MutableLiveData<Int>().apply { value = TipoDeJogo.MEGA_SENA }
    val tipoApostaSelecionada = MutableLiveData<Int>().apply { value = TipoDeJogo.MEGA_SENA }

    val ultimosConcursos = MutableLiveData<List<Int>>()

    fun alterarUsuario(user: Usuario?) {
        usuario.value = user
        usuarioLogado.value = user?.let { true }?: false
    }

    fun irParaTela(numTela: Int, tipoJogo: Int? = tipoResultadoSelecionado.value) {
        tipoResultadoSelecionado.value = tipoJogo
        tela.value = numTela
    }

    fun carregarPropriedadesDosJogos(contexto: Context) {
        val jogos: Array<String> = contexto.resources.getStringArray(R.array.nomes_jogos)
        val lista: MutableList<DadosDoJogo> = mutableListOf()

        jogos.forEachIndexed { index, _ ->
            lista.add(DadosDoJogo(index, contexto))
        }

        propriedadesDosJogos = lista
    }

    fun getJogos(tipo: Int): List<Resultado>? {
        return jogos.value?.filterIndexed { index, jogo ->  jogo.tipoJogo == tipo}
    }

    fun getResultado(tipo: Int, numConcurso: Int): Resultado? {
        return jogos.value?.find { jogo ->  jogo.tipoJogo == tipo && jogo.concurso == numConcurso}
    }

    fun getResultado(tipo: Int, numConcurso: Int, contexto: Context): Resultado? {
        val resultado = getResultado(tipo, numConcurso)
        if (resultado == null) {
            DownloadTask(numConcurso, tipo, contexto).execute()
        }
        return resultado
    }

    fun getPropriedade(tipoJogo: Int): DadosDoJogo {
        return propriedadesDosJogos[tipoJogo]
    }

    fun adicionarJogo(jogo: Resultado) {
        if (getResultado(jogo.tipoJogo, jogo.concurso) == null) {
            jogos.value = jogos.value?.plus(jogo)?: listOf(jogo)
        }
    }

    fun buscarResultato(numConcurso: Int, tipoJogo: Int, contexto: Context) {
        DownloadTask(numConcurso, tipoJogo, contexto).execute()
    }

    inner class InstanciarDBTask(val context: Context): AsyncTask<Unit, Unit, PersistSQLite>() {
        override fun doInBackground(vararg params: Unit?): PersistSQLite {
            val db = JogosDB.get(context)
            return PersistSQLite(db.jogosDAO())
        }
    }

    inner class DownloadTask(val concurso: Int, val tipoJogo: Int, val contexto: Context): AsyncTask<Int, Unit, Resultado>() {

        override fun doInBackground(vararg params: Int?): Resultado? {
            var result: Resultado? = null

            if (!isCancelled) {
                try {
                    result = Resultado(concurso, tipoJogo, contexto)
                } catch (e: Exception) {
                    Log.d("ERRO", e.message)
                    e.message
                }
            }
            return result
        }

        override fun onPostExecute(result: Resultado?) {
            result?.let {
                adicionarJogo(it)
            }
        }
    }

    fun atualizarPerfil(usuario: Usuario): Boolean {
        return if (persist.atualizarUsuario(usuario)) {
            alterarUsuario(usuario)
            true
        } else {
            false
        }
    }

    fun salvarPerfil(usuario: Usuario): Boolean {
        return persist.criarUsuario(usuario)
    }

    fun buscarPerfil(email: String): Usuario? {
        return persist.buscarUsuario(email)
    }
}