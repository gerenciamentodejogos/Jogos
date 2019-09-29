package com.example.gerenciamentodejogos.persistencia

val NOME_DB = "JogosLoteria.db"
val VERSAO_DB = 1
val TABELA_RESULTADOS = "TBResultados"
val SCRIPT_CRIAR_TABELA_RESULTADOS = "CREATE TABLE $TABELA_RESULTADOS (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
        "TipoJogo INTEGER NOT NULL, Concurso INTEGER NOT NULL, Resultado TEXT NOT NULL);"
