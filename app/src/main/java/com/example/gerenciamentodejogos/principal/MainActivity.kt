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
import com.example.gerenciamentodejogos.dados.PROXIMOS_CONCURSOS
import com.example.gerenciamentodejogos.resultados.*
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var VMResultados: ResultadosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarVMResultados()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container_fragmentos, FragmentoPrincipal())
        fragmentTransaction.commit()


        drawerLayout = findViewById(R.id.activity_main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.texto_abrir_menu, R.string.texto_fechar_menu)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById(R.id.navView)

        setUpListeners()
    }

    private fun configurarVMResultados() {
        VMResultados = ViewModelProviders.of(this)[ResultadosViewModel::class.java].apply { carregarPropriedadesDosJogos(resources) }
        VMResultados.jogoSelecionadoResultados.observe(this, Observer { tipoJogo ->
            tipoJogo?.let {it ->
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container_fragmentos, FragmentoResultados())
                fragmentTransaction.commit()
            }
        })

        VMResultados.ultimoConcurso.value = obterUltimosConcursos()
    }

    private fun setUpListeners() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            val novoFragmento: Fragment?

            when (id) {
                R.id.menu_item_inicio -> novoFragmento = FragmentoPrincipal()
                R.id.menu_item_apostas -> novoFragmento = FragmentoApostas()
                R.id.menu_item_resultados -> novoFragmento = FragmentoResultados()

                else -> novoFragmento = null
            }

            if (novoFragmento != null) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container_fragmentos, novoFragmento)
                fragmentTransaction.commit()
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
        PROXIMOS_CONCURSOS.forEachIndexed { tipo, numConcurso ->
            VMResultados.getResultado(tipo, numConcurso, resources)
        }
        return PROXIMOS_CONCURSOS
    }
}
