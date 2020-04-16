package com.kafedra.aaapp.service

import com.kafedra.aaapp.Role
import com.kafedra.aaapp.domain.DBWrapper
import com.kafedra.aaapp.domain.Permission

class Authorization(private val dbWrapper: DBWrapper) {

    fun validateRole(role: String) = Role.values().any { it.role == role }

    fun hasPermission(permission: Permission): Boolean {
        var permRegex = permission.res
        while (permRegex.contains(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)")))
            permRegex = permRegex.replace(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)"), "(\\\\$1)?")
        permRegex = "^$permRegex$"
        return dbWrapper.hasPermission(permission.user, permission.role.toString(), permRegex)
    }
}
