package com.example.gerenciamentodejogos.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.Resources
import android.os.AsyncTask
import android.util.Log
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.modelos.DadosDoJogo
import com.example.gerenciamentodejogos.modelos.Jogo

class ResultadosViewModel: ViewModel() {
    val propriedadesDosJogos = MutableLiveData<List<DadosDoJogo>>()

    val jogos = MutableLiveData<List<Jogo>>()
    val tipoResultadoSelecionado = MutableLiveData<Int>()
    //val jogoSelecionadoResultados = MutableLiveData<Int>()
    //val concursoAtual = MutableLiveData<Int>()
    val ultimoConcurso = MutableLiveData<List<Int>>()

    fun carregarPropriedadesDosJogos(recursos: Resources) {
        val jogos: Array<String> = recursos.getStringArray(R.array.nomes_jogos)
        val lista: MutableList<DadosDoJogo> = mutableListOf()

        jogos.forEachIndexed { index, _ ->
            lista.add(DadosDoJogo(index, recursos))
        }

        propriedadesDosJogos.value = lista
    }

    fun getJogos(tipo: Int): List<Jogo>? {
        return jogos.value?.filterIndexed { index, jogo ->  jogo.tipoJogo == tipo}
    }

    fun getResultado(tipo: Int, numConcurso: Int): Jogo? {
        return jogos.value?.find { jogo ->  jogo.tipoJogo == tipo && jogo.concurso == numConcurso}
    }

    fun getResultado(tipo: Int, numConcurso: Int, recursos: Resources): Jogo? {
        val resultado = getResultado(tipo, numConcurso)
        if (resultado == null) {
            DownloadTask(numConcurso, tipo, recursos).execute()
        }
        return resultado
    }

    fun getPropriedade(tipoJogo: Int): DadosDoJogo? {
        return propriedadesDosJogos.value?.let {
            it[tipoJogo]
        }
    }

    fun adicionarJogo(jogo: Jogo) {
        if (getResultado(jogo.tipoJogo, jogo.concurso) == null) {
            jogos.value = jogos.value?.plus(jogo)?: listOf(jogo)
        }
    }

    fun buscarResultato(numConcurso: Int, tipoJogo: Int, recursos: Resources) {
        DownloadTask(numConcurso, tipoJogo, recursos).execute()
    }

    inner class DownloadTask(val concurso: Int, val tipoJogo: Int, val recursos: Resources): AsyncTask<Int, Unit, Jogo>() {

        override fun doInBackground(vararg params: Int?): Jogo? {
            var result: Jogo? = null

            if (!isCancelled) {
                try {
                    result = Jogo(concurso, tipoJogo, recursos)
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