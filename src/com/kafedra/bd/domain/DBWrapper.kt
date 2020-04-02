package com.kafedra.bd.domain

import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import com.kafedra.bd.domain.Activity
import java.io.File
import java.sql.*

class DBWrapper {
    private var con: Connection? = null

    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun getUser(login: String): User = com.kafedra.bd.users.first { it.login == login }

    fun getPermissions(login: String): List<Permission> =
        com.kafedra.bd.permissions.filter { it.user.login == login }

    fun addActivity(activity: Activity) {
        com.kafedra.bd.activities.add(activity)
    }

    fun loginExists(login: String) = com.kafedra.bd.users.any { it.login == login }

    fun initDatabase(users: List<User>, permissions: List<Permission>) {
        con = DriverManager.getConnection("jdbc:h2:./aaa;MV_STORE=FALSE", "se", "")
        val st = con!!.createStatement()

        st.execute("CREATE TABLE users(login VARCHAR(10) PRIMARY KEY, hash VARCHAR(64), salt VARCHAR(32));")
        for (i in users.indices) {
            val u = users[i]
            st.execute("INSERT INTO users VALUES (${i}, '${u.login}', '${u.hash}', '${u.salt}');")
        }

        st.execute("CREATE TABLE permissions(id INT PRIMARY KEY, res VARCHAR(255), role VARCHAR(7), login VARCHAR(10)  REFERENCES users (login));")
        for (i in permissions.indices) {
            val p = permissions[i]
            st.execute("INSERT INTO permissions VALUES (${i}, '${p.res}', '${p.role}', '${p.user}');")
        }

        st.execute("CREATE TABLE activities(id INT PRIMARY KEY, login VARCHAR(10) REFERENCES users (login), res VARCHAR(255), role VARCHAR(7), ds VARCHAR(10), de VARCHAR(10), vol INT);")
    }

    fun connect(url: String, login: String, pass: String) {
        con = DriverManager.getConnection(url, login, pass)
    }
}
