package com.kafedra.bd.domain

import com.kafedra.bd.Role
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class DBWrapper {
    private var con: Connection? = null

    fun dbExists(): Boolean = File("aaa.h2.db").exists()

    fun getUser(login: String): User {
        val getUser = con!!.prepareStatement("SELECT hash, salt FROM users WHERE login = ?")
        getUser.setString(1, login)
        val res = getUser.executeQuery()
        res.next()
        return User(login, res.getString("salt"), res.getString("hash"))
    }

    fun getPermissions(login: String): List<Permission> {
        val getPerms = con!!.prepareStatement("SELECT res, role FROM permissions WHERE login = ?")
        getPerms.setString(1, login)
        val res = getPerms.executeQuery()
        res.next()
        val perms = mutableListOf<Permission>()
        while (!res.isAfterLast) {
            perms.add(Permission(res.getString("res"), Role.valueOf(res.getString("role")), getUser(login)))
            res.next()
        }
        return perms
    }

    fun addActivity(activity: Activity) {
        val addAct = con!!.prepareStatement("INSERT INTO activities(login, res, role, ds, de, vol) VALUES (?, ?, ?, ?, ?, ?)")
        addAct.setString(1, activity.user.login)
        addAct.setString(2, activity.res)
        addAct.setString(3, activity.role.toString())
        addAct.setString(4, activity.ds)
        addAct.setString(5, activity.de)
        addAct.setInt(6, activity.vol)
        addAct.execute()
    }

    fun loginExists(login: String): Boolean {
        val getUser = con!!.prepareStatement("SELECT count(*) FROM users WHERE login = ?")
        getUser.setString(1, login)
        val res = getUser.executeQuery()
        res.next()
        res.getInt(1)
        return res.getInt(1) > 0
    }

    fun initDatabase(users: List<User>, permissions: List<Permission>) {
        con = DriverManager.getConnection("jdbc:h2:./aaa;MV_STORE=FALSE", "se", "")
        val st = con!!.createStatement()

        st.execute("CREATE TABLE users(login VARCHAR(10) PRIMARY KEY, hash VARCHAR(64), salt VARCHAR(32));")
        for (i in users.indices) {
            val u = users[i]
            st.execute("INSERT INTO users VALUES ('${u.login}', '${u.hash}', '${u.salt}');")
        }

        st.execute("CREATE TABLE permissions(id INT PRIMARY KEY, res VARCHAR(255), role VARCHAR(7), login VARCHAR(10)  REFERENCES users (login));")
        for (i in permissions.indices) {
            val p = permissions[i]
            st.execute("INSERT INTO permissions VALUES (${i}, '${p.res}', '${p.role}', '${p.user.login}');")
        }

        st.execute("CREATE TABLE activities(id INT PRIMARY KEY AUTO_INCREMENT, login VARCHAR(10) REFERENCES users (login), res VARCHAR(255), role VARCHAR(7), ds VARCHAR(10), de VARCHAR(10), vol INT);")
    }

    fun connect(url: String, login: String, pass: String) {
        con = DriverManager.getConnection(url, login, pass)
    }
}
