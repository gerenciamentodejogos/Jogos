package br.com.dev.jogosdaloteria.persistencia

import br.com.dev.jogosdaloteria.modelos.Jogo
import br.com.dev.jogosdaloteria.modelos.Usuario

class PersistSQLite(private val jogosDAO: IJogosDAO) {

    fun criarUsuario(usuario: Usuario): Boolean {
        return try {
            jogosDAO.criarUsurio(usuario)
            true
        } catch (erro: Exception) {
            false
        }
    }

    fun buscarUsuario(email: String): Usuario? {
        return jogosDAO.buscarUsuario(email)
    }

    fun atualizarUsuario(usuario: Usuario): Boolean {
        return try {
            jogosDAO.atualizarUsuario(usuario)
            true
        } catch (erro: Exception) {
            false
        }
    }

//    fun adicionarJogo(jogo: Jogo): Boolean {
//        return try {
//            jogosDAO.adicionarJogo(jogo)
//            true
//        } catch (erro: Exception) {
//            false
//        }
//    }
//
//    fun buscarJogo(tipoJogo: Int, numConcurso: Int): Jogo? {
//        return jogosDAO.buscarJogo(tipoJogo, numConcurso)
//    }
//
//    fun buscarJogosPorTipo(tipoJogo: Int): List<Jogo>? {
//        return jogosDAO.buscarJogosPorTipo(tipoJogo)
//    }
}
