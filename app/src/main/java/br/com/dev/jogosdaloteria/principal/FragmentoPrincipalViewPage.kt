package br.com.dev.jogosdaloteria.principal


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.dev.jogosdaloteria.R
import br.com.dev.jogosdaloteria.TELA_RESULTADOS
import br.com.dev.jogosdaloteria.modelos.DadosDoJogo
import br.com.dev.jogosdaloteria.resultados.FragmentoProximoConcurso
import br.com.dev.jogosdaloteria.resultados.FragmentoResultadoInfoPrincipais
import br.com.dev.jogosdaloteria.view_models.ResultadosViewModel
import br.com.dev.jogosdaloteria.view_models.TelaPrincipalViewModel
import kotlinx.android.synthetic.main.fragmento_principal_view_page.*

class FragmentoPrincipalViewPage : Fragment() {

    private lateinit var vmTelaPrincipal: TelaPrincipalViewModel
    private lateinit var vmResultados: ResultadosViewModel

    private var tipoJogo: Int = 0
    private var numeroConcurso:Int = 1

    var resCarregado = false
    var apostaCarregada = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            numeroConcurso = it.getInt(NUMERO_CONCURSO)
            tipoJogo = it.getInt(TIPO_JOGO)
        }

        val fragResPrincipal = childFragmentManager.beginTransaction()
        fragResPrincipal.replace(R.id.container_res_principal, FragmentoResultadoInfoPrincipais.newInstance(numeroConcurso, tipoJogo))
        fragResPrincipal.commit()

        val fragProxConcurso = childFragmentManager.beginTransaction()
        fragProxConcurso.replace(R.id.container_proximo_concurso, FragmentoProximoConcurso.newInstance(tipoJogo))
        fragProxConcurso.commit()

//        val fragApostaPrincipal = childFragmentManager.beginTransaction()
//        fragApostaPrincipal.add(R.id.container_aposta_principal, FragmentoAPOSTA.newInstance(numeroConcurso, tipoJogo))
//        fragApostaPrincipal.commit()

        configurarVM()

        return inflater.inflate(R.layout.fragmento_principal_view_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atualizarInterface()

        container_res_principal.setOnClickListener {
            vmResultados.irParaTela(TELA_RESULTADOS, tipoJogo)
        }
    }

    private fun atualizarInterface() {
        textView_label_progresso_principal.text = getString(R.string.texto_carregando_ultimo_concurso)
        textView_nome_jogo_tela_principal.text = vmResultados.getPropriedade(tipoJogo).nome
        textView_nome_jogo_tela_principal.setTextColor(vmResultados.getPropriedade(tipoJogo).corPrimaria)
        image_view_icone_jogo_tela_principal.setImageResource(vmResultados.getPropriedade(tipoJogo).icone())
    }

    private fun configurarVM() {
        activity?.let {
            vmResultados = ViewModelProviders.of(it)[ResultadosViewModel::class.java]
        }

        vmTelaPrincipal = ViewModelProviders.of(this)[TelaPrincipalViewModel::class.java]

        vmTelaPrincipal.dadosFragDetalhesCarregados.observe(this, Observer { telaCarregada ->
            if (telaCarregada != null) {
                if (telaCarregada) {
                    resCarregado = telaCarregada
                    vmTelaPrincipal.dadosFragDetalhesCarregados.removeObservers(this)
                    esconderProgresso()
                }
            }
        })
        vmTelaPrincipal.dadosFragApostasCarregados.observe(this, Observer { telaCarregada ->
            if (telaCarregada != null) {
                if (true) {
                    apostaCarregada = true//telaCarregada
                    vmTelaPrincipal.dadosFragApostasCarregados.removeObservers(this)
                    esconderProgresso()
                }
            }
        })
    }

    private fun esconderProgresso() {
        if (apostaCarregada && resCarregado) {
            linear_progresso_principal.visibility = View.GONE
        }
    }

    companion object {
        val NUMERO_CONCURSO = "numero_concurso"
        val TIPO_JOGO = "tipo_jogo"

        fun newInstance(numConcurso: Int, tipoJogo: Int): FragmentoPrincipalViewPage {
            val fragmentoPrincipalViewPage = FragmentoPrincipalViewPage()
            fragmentoPrincipalViewPage.arguments = Bundle().apply {
                putInt(TIPO_JOGO, tipoJogo)
                putInt(NUMERO_CONCURSO, numConcurso)
            }
            return fragmentoPrincipalViewPage
        }
    }

}
