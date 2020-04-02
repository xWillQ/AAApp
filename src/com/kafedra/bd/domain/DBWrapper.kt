package com.kafedra.bd.domain

import com.kafedra.bd.domain.Permission
import com.kafedra.bd.domain.User
import com.kafedra.bd.domain.Activity
import java.io.File

class DBWrapper {
    fun dbExists(): Boolean = File("aaa.db").exists()
    fun getUser(login: String): User = com.kafedra.bd.users.first { it.login == login }
    fun getPermissions(login: String): List<Permission> =
        com.kafedra.bd.permissions.filter { it.user.login == login }

    fun addActivity(activity: Activity) {
        com.kafedra.bd.activities.add(activity)
    }
    fun loginExists(login: String) = com.kafedra.bd.users.any { it.login == login }


}
