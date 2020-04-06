package com.kafedra.bd.domain

import com.kafedra.bd.Role
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import org.flywaydb.core.Flyway
import org.apache.logging.log4j.LogManager
import java.io.Closeable

class DBWrapper: Closeable {
    private var con: Connection? = null
    private val logger = LogManager.getLogger()

    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun getUser(login: String): User {
        logger.info("Get prepared statement with users")
        val getUser = con!!.prepareStatement("SELECT hash, salt FROM users WHERE login = ?")
        getUser.setString(1, login)
        logger.info("Get result set with user")
        val res = getUser.executeQuery()
        res.next()
        val salt = res.getString("salt")
        val hash = res.getString("hash")
        logger.info("Close result set with user")
        res.close()
        logger.info("Close prepared statement with users")
        getUser.close()
        return User(login, salt, hash)
    }

    fun hasPermission(login: String, role: String, permissionRegex: String): Boolean {
        logger.info("Get prepared statement with permission")
        val getPermission = con!!.prepareStatement(
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

    fun addActivity(activity: Activity) {
        logger.info("Get prepared statement with activities")
        val addAct = con!!.prepareStatement(
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

    fun loginExists(login: String): Boolean {
        logger.info("Get prepared statement with user")
        val getUser = con!!.prepareStatement("SELECT count(*) FROM users WHERE login = ?")
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
        val flyway = Flyway.configure().dataSource("${url};MV_STORE=FALSE", login, pass).locations("filesystem:db").load()
        flyway.migrate()
    }

    fun connect(url: String, login: String, pass: String) {
        logger.info("Сonnecting to database")
        con = DriverManager.getConnection(url, login, pass)
    }

    override fun close() {
        logger.info("Disconnecting from database")
        con?.close()
    }

}