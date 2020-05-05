package com.kafedra.aaapp.domain

import com.google.inject.Inject
import com.kafedra.aaapp.di.ConnectionProvider
import java.io.Closeable
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import org.apache.logging.log4j.LogManager
import org.flywaydb.core.Flyway

class DBWrapper @Inject constructor(private val conProvider: ConnectionProvider) {
    private var con: Connection? = null
    private val logger = LogManager.getLogger()

    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun getUser(login: String) = conProvider.get().use<Connection, User> {
        logger.info("Get prepared statement with users")
        val getUser = it.prepareStatement("SELECT id, hash, salt FROM users WHERE login = ?")
        getUser.setString(1, login)
        logger.info("Get result set with user")
        val res = getUser.executeQuery()
        res.next()
        val id = res.getInt("id")
        val salt = res.getString("salt")
        val hash = res.getString("hash")
        logger.info("Close result set with user")
        res.close()
        logger.info("Close prepared statement with users")
        getUser.close()
        return User(id, login, salt, hash)
    }

    @Suppress("MagicNumber")
    fun hasPermission(login: String, role: String, permissionRegex: String) = conProvider.get().use<Connection, Boolean> {
        logger.info("Get prepared statement with permission")
        val getPermission = it.prepareStatement(
                "SELECT count(*) FROM permissions WHERE login = ? and role = ? and res REGEXP ?")
        getPermission.setString(1, login)
        getPermission.setString(2, role)
        logger.info("Matching resources against '$permissionRegex'")
        getPermission.setString(3, permissionRegex)
        logger.info("Get result set with permission")
        val res = getPermission.executeQuery()
        res.next()
        val ans = res.getInt(1) > 0
        logger.info("Close result set with permission")
        res.close()
        logger.info("Close prepared statement with permission")
        getPermission.close()
        return ans
    }

    @Suppress("MagicNumber") // Will be fixed later. Maybe
    fun addActivity(activity: Activity) = conProvider.get().use {
        logger.info("Get prepared statement with activities")
        val addAct = it.prepareStatement(
                "INSERT INTO " +
                        "activities(login, res, role, ds, de, vol) " +
                        "VALUES (?, ?, ?, ?, ?, ?)"
        )
        addAct.setString(1, activity.user.login)
        addAct.setString(2, activity.res)
        addAct.setString(3, activity.role.toString())
        addAct.setString(4, activity.ds)
        addAct.setString(5, activity.de)
        addAct.setInt(6, activity.vol)
        addAct.execute()
        logger.info("Close prepared statement with activities")
        addAct.close()
    }

    fun loginExists(login: String) = conProvider.get().use<Connection, Boolean> {
        logger.info("Get prepared statement with user")
        val getUser = it.prepareStatement("SELECT count(*) FROM users WHERE login = ?")
        getUser.setString(1, login)
        logger.info("Get result set with user")
        val res = getUser.executeQuery()
        res.next()
        val ans = res.getInt(1) > 0
        logger.info("Close result set with user")
        res.close()
        logger.info("Close prepared statement with user")
        getUser.close()
        return ans
    }

    fun initDatabase(url: String, login: String, pass: String) {
        val flyway = Flyway.configure().dataSource("$url;MV_STORE=FALSE", login, pass).locations("classpath:db").load()
        flyway.migrate()
    }
}
