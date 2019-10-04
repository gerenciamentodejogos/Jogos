package br.com.dev.jogosdaloteria.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TBUsuarios")
class Usuario(val nome: String = "",
              @PrimaryKey val email: String,
              val foto: ByteArray? = null)