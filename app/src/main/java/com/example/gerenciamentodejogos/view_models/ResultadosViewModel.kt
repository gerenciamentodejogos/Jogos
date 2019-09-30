package com.example.gerenciamentodejogos.view_models

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.res.Resources
import android.os.AsyncTask
import android.util.Log
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.TELA_INICIAL
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.modelos.Jogo
import com.example.gerenciamentodejogos.modelos.TipoDeJogo
import com.example.gerenciamentodejogos.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ResultadosViewModel: ViewModel() {
    var propriedadesDosJogos: List<DadosDoJogo> = listOf()
    val usuarioLogado = MutableLiveData<Boolean>().apply { value = false }
    val usuario = MutableLiveData<FirebaseUser>()
    val logar = MutableLiveData<Boolean>().apply { value = false }
    var usuarioParaLogin = MutableLiveData<Usuario>()

    val jogos = MutableLiveData<List<Jogo>>()

    val tela = MutableLiveData<Int>().apply { value = TELA_INICIAL }

    val tipoResultadoSelecionado = MutableLiveData<Int>().apply { value = TipoDeJogo.MEGA_SENA }
    val tipoApostaSelecionada = MutableLiveData<Int>().apply { value = TipoDeJogo.MEGA_SENA }

    val ultimosConcursos = MutableLiveData<List<Int>>()

    fun alterarUsuario(user: FirebaseUser?) {
        usuario.value = user
        usuarioLogado.value = user?.let { true }?: false
    }

    fun irParaTela(numTela: Int, tipoJogo: Int? = tipoResultadoSelecionado.value) {
        tipoResultadoSelecionado.value = tipoJogo
        tela.value = numTela
    }

    fun login() { logar.value = true }
    fun logout() { logar.value = false }

    fun carregarPropriedadesDosJogos(contexto: Context) {
        val jogos: Array<String> = contexto.resources.getStringArray(R.array.nomes_jogos)
        val lista: MutableList<DadosDoJogo> = mutableListOf()

        jogos.forEachIndexed { index, _ ->
            lista.add(DadosDoJogo(index, contexto))
        }

        propriedadesDosJogos = lista
    }

    fun getJogos(tipo: Int): List<Jogo>? {
        return jogos.value?.filterIndexed { index, jogo ->  jogo.tipoJogo == tipo}
    }

    fun getResultado(tipo: Int, numConcurso: Int): Jogo? {
        return jogos.value?.find { jogo ->  jogo.tipoJogo == tipo && jogo.concurso == numConcurso}
    }

    fun getResultado(tipo: Int, numConcurso: Int, contexto: Context): Jogo? {
        val resultado = getResultado(tipo, numConcurso)
        if (resultado == null) {
            DownloadTask(numConcurso, tipo, contexto).execute()
        }
        return resultado
    }

    fun getPropriedade(tipoJogo: Int): DadosDoJogo {
        return propriedadesDosJogos[tipoJogo]
    }

    fun adicionarJogo(jogo: Jogo) {
        if (getResultado(jogo.tipoJogo, jogo.concurso) == null) {
            jogos.value = jogos.value?.plus(jogo)?: listOf(jogo)
        }
    }

    fun buscarResultato(numConcurso: Int, tipoJogo: Int, contexto: Context) {
        DownloadTask(numConcurso, tipoJogo, contexto).execute()
    }

    inner class DownloadTask(val concurso: Int, val tipoJogo: Int, val contexto: Context): AsyncTask<Int, Unit, Jogo>() {

        override fun doInBackground(vararg params: Int?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled) {
                try {
                    result = Jogo(concurso, tipoJogo, contexto)
                } catch (e: Exception) {
                    Log.d("ERRO", e.message)
                    e.message
                }
            }
            return result
        }

        override fun onPostExecute(result: Jogo?) {
            result?.let {
                adicionarJogo(it)
            }
        }
    }

}