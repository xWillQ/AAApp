package com.kafedra.bd.service

import com.kafedra.bd.Role
import com.kafedra.bd.domain.DBWrapper
import com.kafedra.bd.domain.Permission

class Authorization {
    companion object {
        val dbWrapper = DBWrapper()
        fun validateRole(role: String) = Role.values().any { it.role == role }

        fun hasPermission(res: String, role: Role, user: String): Boolean {
            val permissions = dbWrapper.getPermissions(user)
            return permissions.any {
                res.contains(Regex("^" + it.res + "\\b")) && it.role == role
            }
        }
    }
}