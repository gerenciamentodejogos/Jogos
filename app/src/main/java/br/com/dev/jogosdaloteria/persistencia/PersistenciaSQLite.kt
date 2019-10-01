package br.com.dev.jogosdaloteria.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.dev.jogosdaloteria.*
import br.com.dev.jogosdaloteria.modelos.Usuario
import java.io.Serializable

class PersistenciaSQLite(override val context: Context): IPersistencia {
    override fun salvarPerfil(usuario: Usuario): Boolean {
        val cv = ContentValues().apply {
            put("Email", usuario.email)
            put("Nome", usuario.nome)
            put("Foto", usuario.foto)
        }

        return gw.getDatabase().insert(TABELA_PERFIL, null, cv) > 0
    }

    override fun buscarPerfil(email: String): Usuario? {
        val cursor: Cursor = gw.getDatabase().rawQuery("SELECT * FROM $TABELA_PERFIL WHERE Email = \"$email\"",null )

        return if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndex("Nome"))
            val foto = cursor.getString(cursor.getColumnIndex("Foto"))
            Usuario(nome, email, "", foto)
        } else {
            null
        }
    }

    private val gw = DBGateway.getInstance(context)

    override fun salvarResultado(tipoJogo: Int, numConcurso: Int, resultado: String): Boolean {
        val cv = ContentValues().apply {
            put("TipoJogo", tipoJogo)
            put("Concurso", numConcurso)
            put("Resultado", resultado)
        }

        return gw.getDatabase().insert(TABELA_RESULTADOS, null, cv) > 0
    }

    override fun buscarResultado(tipoJogo: Int, numConcurso: Int): String? {
        val cursor: Cursor = gw.getDatabase().rawQuery("SELECT * FROM $TABELA_RESULTADOS WHERE TipoJogo = $tipoJogo AND Concurso = $numConcurso", null)

        return if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex("Resultado"))
        } else {
            null
        }
    }
}

class TBResultado(val id: Int, val tipoJogo: Int, val numConcurso: Int, val resultado: String): Serializable {
    override fun equals(other: Any?): Boolean {
        val temp = other as TBResultado
        return id == temp.id
    }

    override fun hashCode(): Int {
        return id
    }
}

class DBGateway(context: Context) {
    private val db: SQLiteDatabase = (object: SQLiteOpenHelper(context,
    NOME_DB, null,
    VERSAO_DB
){
        override fun onCreate(db: SQLiteDatabase?) { db?.execSQL(SCRIPT_CRIAR_TABELAS) }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }).writableDatabase

    fun getDatabase(): SQLiteDatabase = db

    companion object {
        private var gw: DBGateway? = null
        fun getInstance(context: Context): DBGateway {
            if (gw == null) {
                gw =
                    DBGateway(context)
                Log.e("INSTANCIA", "CRIADO")
            }
            return gw!!
        }
    }
}
