package com.kafedra.bd.domain

import com.kafedra.bd.Role

data class Activity(val user: User, val res: String, val role: Role, val ds: String, val de: String, val vol: Int)