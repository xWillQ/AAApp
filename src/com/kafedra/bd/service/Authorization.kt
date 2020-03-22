package com.kafedra.bd.service

import com.kafedra.bd.Role
import com.kafedra.bd.domain.permissions

class Authorization {
    companion object {
        fun validateRole(role: String) = Role.values().any { it.role == role }

        fun hasPermission(res: String, role: Role, user: String): Boolean {
            return permissions.any {
                res.contains(Regex("^" + it.res + "\\b")) &&
                        it.role == role &&
                        it.user.login == user
            }
        }
    }
}