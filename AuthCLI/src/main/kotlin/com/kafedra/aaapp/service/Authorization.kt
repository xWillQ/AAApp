package com.kafedra.aaapp.service

import com.kafedra.aaapp.Role
import com.kafedra.aaapp.domain.Authority
import com.kafedra.aaapp.domain.DBWrapper

class Authorization(private val dbWrapper: DBWrapper) {

    fun validateRole(role: String) = Role.values().any { it.role == role }

    fun hasAuthority(authority: Authority): Boolean {
        var resRegex = authority.res
        while (resRegex.contains(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)")))
            resRegex = resRegex.replace(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)"), "(\\\\$1)?")
        resRegex = "^$resRegex$"
        return dbWrapper.hasAuthority(authority.user, authority.role.toString(), resRegex)
    }

    fun getAuthorityId(authority: Authority): Int {
        var resRegex = authority.res
        while (resRegex.contains(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)")))
            resRegex = resRegex.replace(Regex("(?<=[A-Z])(\\.[A-Z]+[^)\\s]*)"), "(\\\\$1)?")
        resRegex = "^$resRegex$"
        return dbWrapper.getAuthorityId(authority.user, authority.role.toString(), resRegex)
    }
}
