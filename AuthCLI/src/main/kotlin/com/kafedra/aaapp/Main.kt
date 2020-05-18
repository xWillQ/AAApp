package com.kafedra.aaapp

import kotlin.system.exitProcess
import org.flywaydb.core.Flyway

fun main(args: Array<String>) {
    // I moved migration here from App.run() because App.run() is executed in WebService, which has its own migrations
    // This way this migration only gets executed when application is launched from CLI
    val url = System.getenv("H2_URL") ?: "jdbc:h2:./aaa"
    val login = System.getenv("H2_LOGIN") ?: "se"
    val pass = System.getenv("H2_PASS") ?: ""
    val flyway = Flyway.configure().dataSource("$url;MV_STORE=FALSE", login, pass).locations("classpath:db").load()
    flyway.migrate()

    val app = App()
    exitProcess(app.run(args).code)
}
