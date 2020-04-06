package com.kafedra.bd.service

import com.kafedra.bd.Role
import com.kafedra.bd.domain.DBWrapper
import com.kafedra.bd.domain.Permission

class Authorization(val dbWrapper: DBWrapper){

    fun validateRole(role: String) = Role.values().any { it.role == role }

    fun hasPermission(res: String, role: Role, user: String): Boolean {
        var permRegex = res
        while(permRegex.contains(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)")))
            permRegex = permRegex.replace(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)"), "(\\\\$1)?")
        permRegex = "^$permRegex$"
        return dbWrapper.hasPermission(user, role.toString(), permRegex)
    }
}