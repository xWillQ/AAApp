package com.kafedra.aaapp.domain

import java.io.File
import org.flywaydb.core.Flyway

class DBWrapper {
    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun initDatabase(url: String, login: String, pass: String) {
        val flyway = Flyway.configure().dataSource("$url;MV_STORE=FALSE", login, pass).locations("classpath:db").load()
        flyway.migrate()
    }
}
