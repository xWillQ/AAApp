package com.kafedra.aaapp.domain

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.di.ConnectionProvider
import java.io.File
import java.sql.Connection
import org.apache.logging.log4j.LogManager
import org.flywaydb.core.Flyway

@Suppress("TooManyFunctions")  // divide into different classes
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
                "SELECT count(*) " +
                        "FROM authorities a " +
                        "INNER JOIN users u ON a.userId = u.id " +
                        "WHERE login = ? and role = ? and res REGEXP ?")
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
                        "activities(userId, res, role, ds, de, vol, authorityId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)"
        )
        addAct.setInt(1, activity.user.id)
        addAct.setString(2, activity.res)
        addAct.setString(3, activity.role.toString())
        addAct.setString(4, activity.ds)
        addAct.setString(5, activity.de)
        addAct.setInt(6, activity.vol)
        addAct.setInt(7, activity.authorityId)
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

        val res = if (id == 0) st.executeQuery("SELECT * FROM users")
        else st.executeQuery("SELECT * FROM users WHERE id = $id")

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

        st.close()
        return@use userList
    }

    fun getAuthority(id: Int) = conProvider.get().use<Connection, List<Authority>> {
        val st = it.createStatement()
        val authoritiesList = mutableListOf<Authority>()

        val res = if (id == 0) st.executeQuery(
                "SELECT a.id, a.res, a.role, u.login " +
                        "FROM authorities a " +
                        "INNER JOIN users u ON a.userid = u.id"
        ) else st.executeQuery(
                "SELECT a.id, a.res, a.role, u.login " +
                        "FROM authorities a " +
                        "INNER JOIN users u ON a.userid = u.id " +
                        "WHERE a.id = $id"
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

        st.close()
        return@use authoritiesList
    }

    fun getAuthorityByUser(userId: Int) = conProvider.get().use<Connection, List<Authority>> {
        val st = it.createStatement()
        val authoritiesList = mutableListOf<Authority>()

        val res = st.executeQuery(
                "SELECT a.id, a.res, a.role, u.login " +
                        "FROM authorities a " +
                        "INNER JOIN users u ON a.userid = u.id " +
                        "WHERE a.userid = $userId"
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

        st.close()

        return@use authoritiesList
    }

    fun getActivity(id: Int) = conProvider.get().use<Connection, List<Activity>> {
        val st = it.createStatement()
        val activitiesList = mutableListOf<Activity>()

        val res = if (id == 0) st.executeQuery(
                "SELECT * FROM activities a " +
                        "INNER JOIN users u ON a.userId = u.id"
        )
        else st.executeQuery(
                "SELECT * FROM activities a " +
                        "INNER JOIN users u ON a.userId = u.id " +
                        "WHERE a.id=$id"
        )

        res.next()
        while (!res.isAfterLast) {
            val currentID = res.getInt("id")
            val ds = res.getString("ds")
            val de = res.getString("de")
            val vol = res.getInt("vol")
            val user = getUser(res.getString("login"))
            val role = Role.valueOf(res.getString("role"))
            val resource = res.getString("res")
            val authorityId = res.getInt("authorityId")
            activitiesList.add(Activity(currentID, user, authorityId, resource, role, ds, de, vol))
            res.next()
        }
        res.close()

        st.close()

        return@use activitiesList
    }
}
