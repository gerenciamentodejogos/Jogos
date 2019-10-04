package br.com.dev.jogosdaloteria.persistencia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.dev.jogosdaloteria.modelos.Jogo
import br.com.dev.jogosdaloteria.modelos.Usuario

@Dao
interface IJogosDAO {

    @Insert
    fun criarUsurio(usuario: Usuario)

    @Update
    fun atualizarUsuario(usuario: Usuario)

    @Query("SELECT * FROM TBUsuarios WHERE email = :email")
    fun buscarUsuario(email: String): Usuario?


//    @Insert
//    fun adicionarJogo(jogo: Jogo)
//
//    @Query("SELECT * FROM TBResultadoJSON WHERE tipoJogo = :tipoJogo AND numConcurso = :numConcurso")
//    fun buscarJogo(tipoJogo: Int, numConcurso: Int): Jogo?
//
//    @Query("SELECT * FROM TBResultadoJSON WHERE tipoJogo = :tipoJogo")
//    fun buscarJogosPorTipo(tipoJogo: Int): List<Jogo>?
}