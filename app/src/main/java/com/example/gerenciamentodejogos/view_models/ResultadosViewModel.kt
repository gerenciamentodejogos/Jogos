package com.example.gerenciamentodejogos.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.gerenciamentodejogos.modelos.Jogo

class ResultadosViewModel: ViewModel() {
    val jogos = MutableLiveData<List<Jogo>>()
    val tipoJogoAtual = MutableLiveData<Int>()
    val tipoJogoSelecionado = MutableLiveData<Int>()
    val concursoAtual = MutableLiveData<Int>()

    fun getJogos(tipo: Int): List<Jogo>? {
        return jogos.value?.filterIndexed { index, jogo ->  jogo.tipoJogo == tipo}
    }

    fun getJogo(tipo: Int, numConcurso: Int): Jogo? {
        return jogos.value?.find { jogo ->  jogo.tipoJogo == tipo && jogo.concurso == numConcurso}
    }

    fun adicionarJogo(jogo: Jogo) {
        if (getJogo(jogo.tipoJogo, jogo.concurso) == null) {
            jogos.value = jogos.value?.plus(jogo)?: listOf(jogo)
        }
    }
}