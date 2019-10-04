package br.com.dev.jogosdaloteria.persistencia

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dev.jogosdaloteria.NOME_BANCO
import br.com.dev.jogosdaloteria.VERSAO_BANCO
import br.com.dev.jogosdaloteria.modelos.Usuario

@Database(entities = [Usuario::class], version = VERSAO_BANCO)
abstract class JogosDB: RoomDatabase() {

    abstract fun jogosDAO(): IJogosDAO

    companion object{
        private var INSTANCE: JogosDB? = null

        fun get(context: Context): JogosDB {
            return INSTANCE?: run {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JogosDB::class.java,
                    NOME_BANCO)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}