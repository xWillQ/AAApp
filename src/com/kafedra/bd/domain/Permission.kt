package com.kafedra.bd.domain

import com.kafedra.bd.Role

data class Permission(val res: String, val role: Role, val user: User)