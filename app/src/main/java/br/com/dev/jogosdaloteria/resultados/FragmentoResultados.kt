package br.com.dev.jogosdaloteria.resultados


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.TELA_RESULTADOS
import br.com.dev.jogosdaloteria.adapters.TipoJogoSpinnerAdapter
import br.com.dev.jogosdaloteria.dados.ULTIMOS_CONCURSOS
import br.com.dev.jogosdaloteria.listarTiposJogos
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo
import br.com.dev.jogosdaloteria.modelos.TipoDeJogo
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
import kotlinx.android.synthetic.main.fragmento_principal_view_page.*
import kotlinx.android.synthetic.main.fragmento_resultados.*

class FragmentoResultados : Fragment() {
    private lateinit var vmResultados: ResultadosViewModel
    private var tipoJogo = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmento_resultados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarVMResultados()
        configurarSpinner()
        setUpPageView()
        setUpListeners()
        atualizarInterface()
    }

    private fun configurarSpinner() {
        context?.let {
            val tipos = listarTiposJogos(it)
            spinner_tipo_jogo.adapter = TipoJogoSpinnerAdapter(it, tipos)
            spinner_tipo_jogo.setSelection(tipoJogo)
            spinner_tipo_jogo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (vmResultados.tipoResultadoSelecionado.value != position) {
                        vmResultados.irParaTela(TELA_RESULTADOS, position)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun atualizarInterface() {
        textView_tipo_jogo_resultados.text = vmResultados.getPropriedade(tipoJogo).nome
        textView_tipo_jogo_resultados.setTextColor(vmResultados.getPropriedade(tipoJogo).corPrimaria)
    }

    private fun configurarVMResultados() {
        activity?.let {
            vmResultados = ViewModelProviders.of(it).get(ResultadosViewModel::class.java)
            tipoJogo = vmResultados.tipoResultadoSelecionado.value?: TipoDeJogo.MEGA_SENA
        }
    }

    private fun setUpPageView() {
        val tipoJogo = vmResultados.tipoResultadoSelecionado.value?: 0
        viewpage_detalhes_sorteio.adapter = DetalhesResultadoPageAdapter(
            ULTIMOS_CONCURSOS[tipoJogo],
            tipoJogo,
            childFragmentManager
        )
        viewpage_detalhes_sorteio.currentItem = (ULTIMOS_CONCURSOS[tipoJogo]) - 1
    }

    fun setUpListeners() {
        textView_tipo_jogo_resultados.setOnClickListener {
            context?.let {

                val builder = AlertDialog.Builder(it)
                builder.setTitle("Selecione um tipo de jogo")
                    .setItems(R.array.nomes_jogos) { _, idJogo ->
                        vmResultados.irParaTela(TELA_RESULTADOS, idJogo)
                    }
                builder.create()
                builder.show()
            }
        }
    }
}
