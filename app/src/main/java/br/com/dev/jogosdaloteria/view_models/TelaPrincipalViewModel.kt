package br.com.dev.jogosdaloteria.view_models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TelaPrincipalViewModel: ViewModel() {
    val dadosFragDetalhesCarregados = MutableLiveData<Boolean>()
    val dadosFragApostasCarregados = MutableLiveData<Boolean>()
}