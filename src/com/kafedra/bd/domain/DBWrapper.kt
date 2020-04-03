package com.kafedra.bd.domain

import com.kafedra.bd.Role
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import org.flywaydb.core.Flyway
import org.apache.logging.log4j.LogManager

class DBWrapper {
    private var con: Connection? = null
    private val logger = LogManager.getLogger()

    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun getUser(login: String): User {
        logger.info("Get prepare statement with users")
        val getUser = con!!.prepareStatement("SELECT hash, salt FROM users WHERE login = ?")
        getUser.setString(1, login)
        logger.info("Get result set with user")
        val res = getUser.executeQuery()
        logger.info("Close prepare statement with users")
        getUser.close()
        res.next()
        val salt = res.getString("salt")
        val hash = res.getString("hash")
        logger.info("Close result set with user")
        res.close()
        return User(login, salt, hash)
    }

    fun getPermissions(login: String): List<Permission> {
        logger.info("Get prepare statement with permissions")
        val getPerms = con!!.prepareStatement("SELECT res, role FROM permissions WHERE login = ?")
        getPerms.setString(1, login)
        logger.info("Get result set with permissions")
        val res = getPerms.executeQuery()
        logger.info("Close prepare statement with permissions")
        getPerms.close()
        res.next()
        val perms = mutableListOf<Permission>()
        while (!res.isAfterLast) {
            perms.add(
                Permission(
                    res.getString("res"),
                    Role.valueOf(res.getString("role")), getUser(login)
                )
            )
            res.next()
        }
        logger.info("Close result set with permissions")
        res.close()
        return perms
    }

    fun addActivity(activity: Activity) {
        logger.info("Get prepare statement with activities")
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
        logger.info("Close prepare statement with activities")
        addAct.close()
    }

    fun loginExists(login: String): Boolean {
        logger.info("Get prepare statement with user")
        val getUser = con!!.prepareStatement("SELECT count(*) FROM users WHERE login = ?")
        getUser.setString(1, login)
        logger.info("Get result set with user")
        val res = getUser.executeQuery()
        logger.info("Close prepare statement with user")
        getUser.close()
        res.next()
        val ans = res.getInt(1) > 0
        logger.info("Close result set with user")
        res.close()
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

    fun disconnect() {
        logger.info("Disconnecting from database")
        con?.close()
    }

}