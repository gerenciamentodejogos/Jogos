package com.example.gerenciamentodejogos.principal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.text.Layout
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.gerenciamentodejogos.*
import com.example.gerenciamentodejogos.apostas.FragmentoApostas
import com.example.gerenciamentodejogos.dados.ULTIMOS_CONCURSOS
import com.example.gerenciamentodejogos.login.FragmentoCadastro
import com.example.gerenciamentodejogos.login.FragmentoLogin
import com.example.gerenciamentodejogos.login.FragmentoPerfil
import com.example.gerenciamentodejogos.resultados.*
import com.example.gerenciamentodejogos.view_models.ResultadosViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var headerView: View

    private lateinit var buttomLogin: TextView
    private lateinit var buttomPerfil: TextView
    private lateinit var textViewNome: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var imageViewFoto: ImageView

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

        headerView = navView.getHeaderView(0)
        buttomLogin = headerView.findViewById(R.id.textView_login)
        buttomPerfil = headerView.findViewById(R.id.textView_perfil)
        textViewNome = headerView.findViewById(R.id.textView_nome)
        textViewEmail = headerView.findViewById(R.id.textView_email)
        imageViewFoto = headerView.findViewById(R.id.imageView_foto)

        setUpListeners()
        atualizarNavHeader()
    }

    private fun atualizarNavHeader() {
        val usuario = vmResultados.usuario.value

        if (usuario != null) {
            textViewNome.text = usuario.displayName
            textViewEmail.text = usuario.email
            buttomPerfil.text = getString(R.string.texto_editar_perfil)
            buttomLogin.text = getString(R.string.texto_logout)
            imageViewFoto.setImageDrawable(obterFoto(usuario.photoUrl))
        } else {
            textViewNome.text = getString(R.string.texto_usuario_anonimo)
            textViewEmail.text = ""
            buttomPerfil.text = getString(R.string.texto_criar_conta)
            buttomLogin.text = getString(R.string.texto_login)
            imageViewFoto.setImageResource(0)
        }
    }

    private fun obterFoto(url: Uri?): Drawable? {
        return url?.let {
            null
        }
    }

    private fun fazerLogin() {
        val usuario = vmResultados.usuarioParaLogin.value
        if (usuario != null) {
            val auth =  FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener(this) { tarefa ->
                if (tarefa.isSuccessful) {
                    vmResultados.alterarUsuario(tarefa.result?.user)
                } else {
                    Toast.makeText(baseContext, "Erro ao fazer login!\nVerifique se o e-mail e senha digitados estão corretos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fazerLogout() {
        FirebaseAuth.getInstance().signOut()
        vmResultados.alterarUsuario(null)
    }

    private fun configurarVMResultados() {
        vmResultados = ViewModelProviders.of(this)[ResultadosViewModel::class.java].apply { carregarPropriedadesDosJogos(baseContext) }

        vmResultados.usuario.observe(this, Observer {
            atualizarNavHeader()
        })

        vmResultados.logar.observe(this, Observer {logar ->
            logar?.let {
                if (it) {
                    fazerLogin()
                } else {
                    fazerLogout()
                }
            }
        })

        vmResultados.tela.observe(this, Observer {tela ->
            supportFragmentManager.beginTransaction().apply {

                val fragmento = when (tela) {
                    TELA_LOGIN -> FragmentoLogin()
                    TELA_CADASTRO -> FragmentoCadastro()
                    TELA_PERFIL -> FragmentoPerfil()
                    TELA_RESULTADOS -> {
                        navView.setCheckedItem(R.id.menu_item_resultados)
                        FragmentoResultados()
                    }
                    TELA_APOSTAS -> {
                        navView.setCheckedItem(R.id.menu_item_apostas)
                        FragmentoApostas()
                    }
                    else -> {
                        navView.setCheckedItem(R.id.menu_item_inicio)
                        FragmentoPrincipal()
                    }
                }
                replace(R.id.container_fragmentos, fragmento)
                commit()
            }
        })

        vmResultados.ultimosConcursos.value = obterUltimosConcursos()
    }

    private fun setUpListeners() {
        buttomLogin.setOnClickListener {
            vmResultados.usuarioLogado.value?.let { logado ->
                if (logado) {
                    vmResultados.logout()
                } else {
                    vmResultados.irParaTela(TELA_LOGIN)
                }
            }
        }

        buttomPerfil.setOnClickListener {
            vmResultados.usuarioLogado.value?.let { logado ->
                if (logado) {
                    vmResultados.irParaTela(TELA_PERFIL)
                } else {
                    vmResultados.irParaTela(TELA_CADASTRO)
                }
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                R.id.menu_item_inicio -> vmResultados.irParaTela(TELA_INICIAL)
                R.id.menu_item_apostas -> vmResultados.irParaTela(TELA_APOSTAS)
                R.id.menu_item_resultados -> vmResultados.irParaTela(TELA_RESULTADOS)
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }
    }

    override fun onBackPressed() {
        if (vmResultados.tela.value != TELA_INICIAL) {
            vmResultados.irParaTela(TELA_INICIAL)
        } else {
            super.onBackPressed()
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
