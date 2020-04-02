package com.kafedra.bd.domain

import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import com.kafedra.bd.domain.Activity
import java.io.File
import java.sql.*

class DBWrapper {
    var con: Connection? = null

    fun dbExists(): Boolean = File("aaa.db").exists()

    fun getUser(login: String): User = com.kafedra.bd.users.first { it.login == login }

    fun getPermissions(login: String): List<Permission> =
        com.kafedra.bd.permissions.filter { it.user.login == login }

    fun addActivity(activity: Activity) {
        com.kafedra.bd.activities.add(activity)
    }

    fun loginExists(login: String) = com.kafedra.bd.users.any { it.login == login }

    fun initDatabase(users: List<User>, permissions: List<Permission>) {
        con = DriverManager.getConnection("jdbc:h2:./aaa.db")
        val st = con!!.createStatement()

        st.execute("CREATE TABLE users(ID INT PRIMARY KEY, LOGIN VARCHAR(255), HASH VARCHAR(255), SALT VARCHAR(255));")
        for (i in users.indices) {
            val u = users[i]
            st.execute("INSERT INTO users VALUES (${i}, '${u.login}', '${u.hash}', '${u.salt}');")
        }

        st.execute("CREATE TABLE permissions(ID INT PRIMARY KEY, RES VARCHAR(255), ROLE VARCHAR(255), LOGIN VARCHAR(255));")
        for (i in permissions.indices) {
            val p = permissions[i]
            st.execute("INSERT INTO permissions VALUES (${i}, '${p.res}', '${p.role}', '${p.user}');")
        }

        st.execute("CREATE TABLE activities(ID INT PRIMARY KEY, LOGIN VARCHAR(255), RES VARCHAR(255), ROLE VARCHAR(255), DS VARCHAR(255), DE VARCHAR(255), VOL VARCHAR(255));")
    }

    fun connect(url: String, login: String, pass: String) {
        con = DriverManager.getConnection(url, login, pass)
    }
}
