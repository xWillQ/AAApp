package com.kafedra.aaapp.service

import com.google.inject.Inject
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.dao.AuthorityDao
import com.kafedra.aaapp.domain.Authority
import com.kafedra.aaapp.domain.DBWrapper

class Authorization {
    @Inject lateinit var dao : AuthorityDao

    fun validateRole(role: String) = Role.values().any { it.role == role }

    fun hasAuthority(authority: Authority): Boolean {
        return dao.hasAuthority(authority.user?.login?:"", authority.role.toString(), authority.res)
    }

    fun getAuthorityId(authority: Authority): Int {
        return dao.getAuthorityId(authority.user?.login?:"", authority.role.toString(), authority.res)
    }
}
