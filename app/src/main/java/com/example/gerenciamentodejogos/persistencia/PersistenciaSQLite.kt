package com.example.gerenciamentodejogos.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.Serializable
import java.sql.Array

class PersistenciaSQLite(override val context: Context): IPersistencia {
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
//    private val db: SQLiteDatabase = PersistenciaSQLite(context).writableDatabase
    private val db: SQLiteDatabase = (object: SQLiteOpenHelper(context, NOME_DB, null, VERSAO_DB){
        override fun onCreate(db: SQLiteDatabase?) { db?.execSQL(SCRIPT_CRIAR_TABELA_RESULTADOS) }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }).writableDatabase

    fun getDatabase(): SQLiteDatabase = db

    companion object {
        private var gw: DBGateway? = null
        fun getInstance(context: Context): DBGateway {
            if (gw == null) {
                gw = DBGateway(context)
                Log.e("INSTANCIA", "CRIADO")
            }
            return gw!!
        }
    }
}
