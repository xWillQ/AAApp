package com.kafedra.aaapp.di

import com.google.inject.Provider
import java.sql.Connection
import java.sql.DriverManager

class ConnectionProvider : Provider<Connection> {
    override fun get(): Connection = DriverManager.getConnection(
            System.getenv("H2_URL") ?: "jdbc:h2:./aaa",
            System.getenv("H2_LOGIN") ?: "se",
            System.getenv("H2_PASS") ?: ""
    )
}
