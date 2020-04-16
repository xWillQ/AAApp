package com.kafedra.aaapp.domain

import com.kafedra.aaapp.Role

data class Permission(val user: String, val role: Role, val res: String)
