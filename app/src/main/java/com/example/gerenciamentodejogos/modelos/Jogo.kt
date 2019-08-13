package com.example.gerenciamentodejogos.modelos

class Jogo(val numConcurso: Int, val dataSorteio: Long) {
    var encerrado = false
    var premioEstimado = 0
    var numSorteados = listOf<Int>()

}