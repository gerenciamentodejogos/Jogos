package com.example.gerenciamentodejogos.activitys

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import com.example.gerenciamentodejogos.R
import com.example.gerenciamentodejogos.apostas.FragmentoApostas
import com.example.gerenciamentodejogos.dados.PROXIMO_CONCURSO
import com.example.gerenciamentodejogos.dados_web.DadosWeb
import com.example.gerenciamentodejogos.fragmentos.FragmentoPrincipal
import com.example.gerenciamentodejogos.resultados.FragmentoResultados
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        PROXIMO_CONCURSO = 2180

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
}
