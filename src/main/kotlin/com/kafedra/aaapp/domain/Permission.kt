package com.kafedra.aaapp.domain

import com.kafedra.aaapp.Role

data class Permission(val res: String, val role: Role, val user: User)
