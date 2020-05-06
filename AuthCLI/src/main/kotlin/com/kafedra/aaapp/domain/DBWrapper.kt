package com.kafedra.aaapp.domain

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.di.ConnectionProvider
import java.io.File
import java.sql.Connection
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
    fun hasAuthority(login: String, role: String, resourceRegex: String) = conProvider.get().use<Connection, Boolean> {
        logger.info("Get prepared statement with authority")
        val getPermission = it.prepareStatement(
                "SELECT count(*) FROM authorities WHERE login = ? and role = ? and res REGEXP ?")
        getPermission.setString(1, login)
        getPermission.setString(2, role)
        logger.info("Matching resources against '$resourceRegex'")
        getPermission.setString(3, resourceRegex)
        logger.info("Get result set with authority")
        val res = getPermission.executeQuery()
        res.next()
        val ans = res.getInt(1) > 0
        logger.info("Close result set with authority")
        res.close()
        logger.info("Close prepared statement with authority")
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

    fun getUser(id: Int) = conProvider.get().use<Connection, List<User>> {
        val st = it.createStatement()
        val userList = mutableListOf<User>()
        if (id == 0) {
            val res = st.executeQuery("SELECT * FROM users")
            res.next()
            while (!res.isAfterLast) {
                val currentID = res.getInt("id")
                val login = res.getString("login")
                val hash = res.getString("hash")
                val salt = res.getString("salt")
                userList.add(User(currentID, login, salt, hash))
                res.next()
            }
            res.close()
        } else {
            val res = st.executeQuery("SELECT login, hash, salt FROM users WHERE id = $id")
            res.next()
            if (!res.isAfterLast) {
                val login = res.getString("login")
                val hash = res.getString("hash")
                val salt = res.getString("salt")
                userList.add(User(id, login, salt, hash))
            }
            res.close()
        }
        st.close()
        return@use userList
    }

    fun getAuthority(id: Int) = conProvider.get().use<Connection, List<Authority>> {
        val st = it.createStatement()
        val authoritiesList = mutableListOf<Authority>()
        if (id == 0) {
            val res = st.executeQuery(
                    "SELECT a.id, a.res, a.role, u.login " +
                            "FROM authorities a " +
                            "INNER JOIN users u ON a.userid = u.id"
            )
            res.next()
            while (!res.isAfterLast) {
                val currentID = res.getInt("id")
                val resource = res.getString("res")
                val role = Role.valueOf(res.getString("role"))
                val user = res.getString("login")
                authoritiesList.add(Authority(currentID, user, role, resource))
                res.next()
            }
            res.close()
        } else {
            val res = st.executeQuery("SELECT a.id, a.res, a.role, u.login" +
                    "FROM authorities a" +
                    "INNER JOIN users u ON a.userid = u.id" +
                    "WHERE a.id = $id"
            )
            res.next()
            if (!res.isAfterLast) {
                val resource = res.getString("res")
                val role = Role.valueOf(res.getString("role"))
                val user = res.getString("login")
                authoritiesList.add(Authority(id, user, role, resource))
            }
            res.close()
        }
        st.close()
        return@use authoritiesList
    }
}
