package com.example.gerenciamentodejogos.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.res.Resources
import android.os.AsyncTask
import android.util.Log
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.modelos.Jogo

class ResultadosViewModel: ViewModel() {
    var propriedadesDosJogos: List<DadosDoJogo> = listOf()

    val jogos = MutableLiveData<List<Jogo>>()

    val tipoResultadoSelecionado = MutableLiveData<Int>()
    val tipoApostaSelecionada = MutableLiveData<Int>()
    val telaPrincipal = MutableLiveData<Boolean>()

    val ultimosConcursos = MutableLiveData<List<Int>>()

    fun irParaTelaPrincipal() { telaPrincipal.value = true }
    fun irParaResultados(tipoJogo: Int = tipoResultadoSelecionado.value?: 0) { tipoResultadoSelecionado.value = tipoJogo }
    fun irParaApostas(tipoJogo: Int = tipoApostaSelecionada.value?: 0) { tipoApostaSelecionada.value = tipoJogo }

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