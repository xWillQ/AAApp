package com.kafedra.aaapp.domain

import com.kafedra.aaapp.Role

data class Activity(
        val id: Int,
        val user: User,
        val res: String,
        val role: Role,
        val ds: String,
        val de: String,
        val vol: Int)
