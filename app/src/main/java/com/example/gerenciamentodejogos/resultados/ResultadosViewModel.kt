package com.example.gerenciamentodejogos.resultados

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ResultadosViewModel: ViewModel() {
    val concurso = MutableLiveData<Int>()
    val dezenasSorteadas = MutableLiveData<List<String>>()
    val data = MutableLiveData<Long>()
    val ganhadores = MutableLiveData<List<String>>()
    val localSorteio = MutableLiveData<String>()
    val premio = MutableLiveData<Float>()
}
