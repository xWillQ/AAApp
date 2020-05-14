package com.kafedra.aaapp.service

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.dao.AuthorityDao
import com.kafedra.aaapp.domain.Authority
import com.kafedra.aaapp.domain.DBWrapper

class Authorization {
    @Inject lateinit var dao : AuthorityDao

    fun validateRole(role: String) = Role.values().any { it.role == role }

    fun hasAuthority(login: String, role: Role, res: String): Boolean {
        return dao.hasAuthority(login, role, res)
    }

    fun getAuthorityId(login: String, role: Role, res: String): Int {
        return dao.getAuthorityId(login, role, res)
    }
}
