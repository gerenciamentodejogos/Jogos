package com.example.gerenciamentodejogos.principal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.apostas.FragmentoApostas
import com.example.gerenciamentodejogos.dados.ULTIMOS_CONCURSOS
import com.example.gerenciamentodejogos.resultados.*
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var vmResultados: ResultadosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarVMResultados()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container_fragmentos, FragmentoPrincipal())
            commit()
        }


        drawerLayout = findViewById(R.id.activity_main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.texto_abrir_menu, R.string.texto_fechar_menu)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById(R.id.navView)

        setUpListeners()
    }

    private fun configurarVMResultados() {
        vmResultados = ViewModelProviders.of(this)[ResultadosViewModel::class.java].apply { carregarPropriedadesDosJogos(baseContext) }
        vmResultados.tipoResultadoSelecionado.observe(this, Observer {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_fragmentos, FragmentoResultados())
                commit()
            }
        })

        vmResultados.tipoApostaSelecionada.observe(this, Observer {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_fragmentos, FragmentoApostas())
                commit()
            }
        })

        vmResultados.telaPrincipal.observe(this, Observer {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_fragmentos, FragmentoPrincipal())
                commit()
            }
        })

        vmResultados.ultimosConcursos.value = obterUltimosConcursos()
    }

    private fun setUpListeners() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                R.id.menu_item_inicio -> vmResultados.irParaTelaPrincipal()
                R.id.menu_item_apostas -> vmResultados.irParaApostas()
                R.id.menu_item_resultados -> vmResultados.irParaResultados()
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun obterUltimosConcursos(): List<Int> {
        ULTIMOS_CONCURSOS.forEachIndexed { tipo, numConcurso ->
            vmResultados.getResultado(tipo, numConcurso, baseContext)
        }

        return ULTIMOS_CONCURSOS
    }
}
