package br.com.dev.jogosdaloteria.view_models


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class TelaPrincipalViewModel: ViewModel() {
    val dadosFragDetalhesCarregados = MutableLiveData<Boolean>()
    val dadosFragApostasCarregados = MutableLiveData<Boolean>()
}